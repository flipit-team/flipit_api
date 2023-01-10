package com.flip.service.services;

import com.flip.data.entity.AppUser;
import com.flip.data.entity.AuthCode;

/**
 * @author Charles on 23/11/2022
 */
public interface EmailService {

    void sendVerificationConfirmationEmail(AppUser user);

    void sendDeactivationEmail(AppUser user);

    void sendVerificationInitiationEmail(AppUser user, AuthCode authCode);
}
