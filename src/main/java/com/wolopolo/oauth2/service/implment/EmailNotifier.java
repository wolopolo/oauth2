package com.wolopolo.oauth2.service.implment;

import com.wolopolo.oauth2.service.Notifier;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.IContext;

import java.io.File;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class EmailNotifier implements Notifier {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Override
    public void pushNotification(String to, String subject, String text, String[] attachments) {
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper;

        try {
            helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);

            if(attachments != null) {
                for(String attachment : attachments) {
                    FileSystemResource file = new FileSystemResource(new File(attachment));
                    helper.addAttachment(Objects.requireNonNull(file.getFilename()), file);
                }
            }
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        javaMailSender.send(message);
    }

    @Override
    public String getTextBodyFromTemplate(String template, IContext context) {
        return templateEngine.process(template, context);
    }
}
