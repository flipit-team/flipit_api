package com.flip.data.repository;

import com.flip.data.entity.AppUser;
import com.flip.data.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;

/**
 * @author Charles on 28/10/2021
 */
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    AppUser findAppUserById(Long id);

    AppUser findByEmail(String email);

    AppUser findByPhoneNumber(String phoneNumber);

    AppUser findByIdAndStatusIn(Long id, List<UserStatus> status);

    Page<AppUser> findByStatus(UserStatus status, Pageable pageable);

    @Query("select u from AppUser u where u.status <> 'Deactivated'")
    Page<AppUser> findAllActiveUsers(Pageable pageable);

    default AppUser findActiveUserById(Long id) {
        return findByIdAndStatusIn(id, Arrays.asList(UserStatus.Verified, UserStatus.Registered));
    }

}
