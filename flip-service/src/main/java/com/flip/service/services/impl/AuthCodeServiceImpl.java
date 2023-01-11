package com.flip.service.services.impl;

import com.flip.data.entity.AppUser;
import com.flip.data.entity.AuthCode;
import com.flip.data.enums.CodeType;
import com.flip.data.repository.AuthCodeRepository;
import com.flip.service.services.AuthCodeService;
import com.flip.service.util.RefUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Charles on 24/02/2022
 */
@Service
public class AuthCodeServiceImpl implements AuthCodeService {

    @Value("${ver_code_expiry_sec}")
    private int verCodeExpirySec;

    @Autowired
    private AuthCodeRepository repository;

    @Override
    public AuthCode createAuthCode(AppUser user, CodeType codeType) {
        AuthCode authCode = new AuthCode();
        authCode.setCode(RefUtil.generateUniqueRef());
        authCode.setAppUser(user);
        authCode.setCodeType(codeType);
        authCode.setExpiryDate(new Date(System.currentTimeMillis() + (1000L * verCodeExpirySec)));
        repository.save(authCode);
        return authCode;
    }

    @Override
    public AuthCode findAuthCode(Long userId, CodeType codeType, String code) {
        return repository.fetchUserAuthCode(userId, codeType, code, new Date());
    }

    @Override
    public void expireCode(AuthCode authCode) {
        if (authCode == null)
            return;

        authCode.setDateUpdated(new Date());
        authCode.setUsed(true);
        repository.save(authCode);
    }
}
