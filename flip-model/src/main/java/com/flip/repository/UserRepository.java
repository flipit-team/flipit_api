package com.flip.repository;

import com.flip.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author Charles on 20/06/2021
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserById(Long id);

    @Query("select u from User u where u.id = :id and u.status <> 'Deactivated'")
    User findActiveUserById(Long id);

    @Query("select u from User u where u.status <> 'Deactivated'")
    Page<User> findAllActiveUsers(Pageable pageable);

    @Query("select u from User u where u.email = :email and u.status <> 'Deactivated'")
    User findUserByEmail(@Param("email") String email);

    @Query("select u from User u where u.verificationCode = :code and u.status <> 'Deactivated'")
    User findUserByVerificationCode(@Param("code") String code);
}
