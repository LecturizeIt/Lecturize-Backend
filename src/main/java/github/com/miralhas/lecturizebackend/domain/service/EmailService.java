package github.com.miralhas.lecturizebackend.domain.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void sendEmailWithHtmlContent(String to, String subject, String htmlFilePath) throws MessagingException, IOException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);

        Path path = Paths.get(htmlFilePath);
        String htmlContent = new String(Files.readAllBytes(path));

        helper.setText(htmlContent, true);

        addInlineImages(helper);

        addAttachments(helper);

        javaMailSender.send(mimeMessage);
    }

    private void addInlineImages(MimeMessageHelper helper) throws MessagingException, IOException {
        Resource imageResource = new ClassPathResource("static/images/LecturizeIt.jpeg");
        helper.addInline("logo", imageResource);
    }

    private void addAttachments(MimeMessageHelper helper) throws MessagingException {
        Resource attachment = new ClassPathResource("static/docs/manual.pdf");
        helper.addAttachment("manual.pdf", attachment);
    }
}
