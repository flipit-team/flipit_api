package com.flip.service.services.impl;

import com.flip.data.entity.AppUser;
import com.flip.data.entity.AuthCode;
import com.flip.service.services.EmailService;
import com.flip.service.services.external.EmailSender;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Charles on 23/11/2022
 */
@Log4j2
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailSender emailSender;

    private static final String domain = "http://localhost:8080";

    @Override
    public void sendVerificationConfirmationEmail(AppUser user) {
        try {
            String subject = "Account Verified";
            String text = "Congratulations "+ user.getFirstName()+"! \n\n Your account has been verified!";
            emailSender.sendTextEmail(subject, text, user.getEmail());
        } catch (Exception ex) {
           log.error("An error occurred while sending verification confirmation email.", ex);
        }
    }

    @Override
    public void sendDeactivationEmail(AppUser user) {
        try {
            String subject = "Account Deactivated";
            String text = "Hi "+ user.getFirstName() +
                    ", \n\n Your Flipi account has been deactivate. Please contact support to re-activate your account.";
            emailSender.sendTextEmail(subject, text, user.getEmail());
        } catch (Exception ex) {
            log.error("An error occurred while sending deactivation email.", ex);
        }
    }

    @Override
    public void sendVerificationInitiationEmail(AppUser user, AuthCode authCode) {
        try {
            String subject = "Account Verification";
            String link = domain +"/api/v1/users/verifyEmail/"+ user.getId() +"?code="+ authCode.getCode();
            String body = "Hi " +
                    user.getFirstName() +
                    ", \n\n Kindly click the link below to verify your Flipi account.\n\n" +
                    link;
            emailSender.sendTextEmail(subject, body, user.getEmail());
        } catch (Exception ex) {
            log.error("An error occurred while sending verification initiation email.", ex);
        }
    }
}
