package com.flip.service.services.external;

import javax.mail.MessagingException;

/**
 * @author Charles on 08/01/2023
 */
public interface EmailSender {

    void sendTextEmail(String subject, String body, String... to);

    void sendHtmlEmail(String subject, String html, String pathToAttachment, String... to) throws MessagingException;
}
