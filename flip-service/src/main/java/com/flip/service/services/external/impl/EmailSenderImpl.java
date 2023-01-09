package com.flip.service.services.external.impl;

import com.flip.service.services.external.EmailSender;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Objects;

/**
 * @author Charles on 08/01/2023
 */
@Service
public class EmailSenderImpl implements EmailSender {

    @Autowired
    private JavaMailSender jms;

    public void sendTextEmail(String subject, String body, String... to) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(body);
        jms.send(msg);
    }

    public void sendHtmlEmail(String subject, String html, String pathToAttachment, String... to) throws MessagingException {
        boolean hasAttachment = StringUtils.isNotEmpty(pathToAttachment);
        MimeMessage msg = jms.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, hasAttachment);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(html, true);

        if (hasAttachment) {
            // attach file
            FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
            helper.addAttachment(Objects.requireNonNull(file.getFilename()), file);
        }
        jms.send(msg);
    }
}
