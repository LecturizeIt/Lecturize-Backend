package github.com.miralhas.lecturizebackend.config.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.ErrorHandler;

import java.util.List;

@Configuration
public class RabbitMQConfig {

	@Bean
	public SimpleMessageConverter messageConverter() {
		var simpleMessageConverter = new SimpleMessageConverter();
		simpleMessageConverter.setAllowedListPatterns(getAllowedListPatterns());
		return simpleMessageConverter;
	}

	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
		var factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setMessageConverter(messageConverter());
		factory.setDefaultRequeueRejected(false);
		factory.setErrorHandler(new FatalExceptionStrategy());

		RetryTemplate retryTemplate = new RetryTemplate();
		ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
		backOffPolicy.setInitialInterval(1000L);
		backOffPolicy.setMultiplier(2.0);
		backOffPolicy.setMaxInterval(10000L);
		retryTemplate.setBackOffPolicy(backOffPolicy);

		SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
		retryPolicy.setMaxAttempts(3);
		retryTemplate.setRetryPolicy(retryPolicy);

		factory.setRetryTemplate(retryTemplate);
		return factory;
	}

	@Slf4j
	public static class FatalExceptionStrategy implements ErrorHandler {
		@Override
		public void handleError(Throwable t) {
			if (t.getCause() instanceof AmqpRejectAndDontRequeueException) {
				log.error("Message rejected and will not be requeued: {}", t.getCause().getMessage());
			} else {
				log.error("Unhandled exception in message listener [{}]: {}", t.getClass().getName(), t.getMessage(), t);
				throw new AmqpRejectAndDontRequeueException("Error handler converted exception to fatal", t);
			}
		}
	}

	private List<String> getAllowedListPatterns() {
		return List.of(
				"github.com.miralhas.lecturizebackend.api.dto.*",
				"java.*"
		);
	}
}
