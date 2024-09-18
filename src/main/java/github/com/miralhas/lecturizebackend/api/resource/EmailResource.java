package github.com.miralhas.lecturizebackend.api.resource;

import github.com.miralhas.lecturizebackend.domain.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class EmailResource {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send-email")
    public String sendEmail(@RequestParam String to) {
        try {
            emailService.sendEmailWithHtmlContent(to, "Email Subject", "src/main/resources/templates/email-template.html");
            return "Email sent successfully!";
        } catch (MessagingException | IOException e) {
            return "Failed to send the email!";
        }
    }

}
