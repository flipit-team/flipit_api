package com.flip.data.repository;

import com.flip.data.entity.AuthCode;
import com.flip.data.enums.CodeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

/**
 * @author Charles on 24/02/2022
 */
public interface AuthCodeRepository extends JpaRepository<AuthCode, Long> {

    @Query("select c from AuthCode c where c.used = false and c.appUser.id = :userId and " +
            "c.codeType = :codeType and c.code = :code and c.expiryDate > :now")
    AuthCode fetchUserAuthCode(@Param("userId") Long userId, @Param("codeType") CodeType codeType,
                                         @Param("code") String code, @Param("now") Date now);
}
