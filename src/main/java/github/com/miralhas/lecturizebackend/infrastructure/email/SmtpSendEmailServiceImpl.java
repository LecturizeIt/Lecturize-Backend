package github.com.miralhas.lecturizebackend.infrastructure.email;

import freemarker.template.Configuration;
import freemarker.template.Template;
import github.com.miralhas.lecturizebackend.domain.service.SendEmailService;
import github.com.miralhas.lecturizebackend.infrastructure.exception.EmailException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

@Service
@RequiredArgsConstructor
public class SmtpSendEmailServiceImpl implements SendEmailService  {

    private final JavaMailSender javaMailSender;
    private final Configuration freemarkerConfig;

    @Override
    public void send(Message message) {
        try {
            String body = processTemplate(message);
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom("lecturizeit");
            helper.setText(body, true);
            helper.setSubject(message.getSubject());
            helper.setTo(message.getRecipients().toArray(new String[]{}));
            helper.addInline("logo", new ClassPathResource("static/images/logo.png"));
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException("Não foi possível enviar o e-mail", e);
        }
    }

    private String processTemplate(Message message) {
        try {
            Template template = freemarkerConfig.getTemplate(message.getBody());
            return FreeMarkerTemplateUtils.processTemplateIntoString(template,  message.getModels());
        } catch (Exception e) {
            throw new EmailException("Não foi possível processar o template do e-mail", e);
        }
    }
}
