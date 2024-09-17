package github.com.miralhas.lecturizebackend.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@Configuration
public class WebConfig {

    @Bean
    public OncePerRequestFilter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }

}
