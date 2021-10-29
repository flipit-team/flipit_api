package com.flip.data.repository;

import com.flip.data.entity.AuthUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author Charles on 20/06/2021
 */
public interface UserRepository extends JpaRepository<AuthUser, Long> {

    AuthUser findUserById(Long id);

    default AuthUser findActiveUserById(Long id) {
        return findByIdAndStatus(id, "Deactivated");
    }

    AuthUser findUserByEmail(String email);

    AuthUser findByIdAndStatus(Long id, String status);

    @Query("select u from AuthUser u where u.status <> 'Deactivated'")
    Page<AuthUser> findAllActiveUsers(Pageable pageable);

    Page<AuthUser> findByStatus(String status, Pageable pageable);

    @Query("select u from AuthUser u where u.verificationCode = :code and u.status <> 'Deactivated'")
    AuthUser findUserByVerificationCode(@Param("code") String code);
}
