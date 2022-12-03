package com.flip.service.services.impl;

import com.flip.data.entity.AppUser;
import com.flip.service.services.EmailService;
import org.springframework.stereotype.Service;

/**
 * @author Charles on 23/11/2022
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Override
    public void sendVerificationConfirmationEmail(AppUser user) {

    }

    @Override
    public void sendDeactivationEmail(AppUser user) {

    }

    @Override
    public void sendVerificationInitiationEmail(AppUser user) {

    }
}
