package com.flip.service.services;

import com.flip.data.entity.AppUser;
import com.flip.data.entity.AuthCode;
import com.flip.data.enums.CodeType;

/**
 * @author Charles on 24/02/2022
 */
public interface AuthCodeService {

    AuthCode createAuthCode(AppUser user, CodeType codeType);

    AuthCode findAuthCode(Long userId, CodeType codeType, String code);

    void expireCode(AuthCode code);
}
