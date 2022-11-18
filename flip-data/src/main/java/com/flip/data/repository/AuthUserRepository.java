package com.flip.data.repository;

import com.flip.data.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Charles on 20/06/2021
 */
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {

    AuthUser findAuthUserById(Long id);

    AuthUser findAuthUserByIdAndEnabled(Long id, boolean enabled);

    AuthUser findAuthUserByUsername(String username);

}
