package com.flip.service.services;

import com.flip.data.entity.AppUser;
import com.flip.service.pojo.request.BvnVerificationRequest;
import com.flip.service.pojo.request.ProfileVerificationRequest;
import com.flip.service.pojo.request.UserRequest;
import com.flip.service.pojo.response.BaseResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * @author Charles on 23/06/2021
 */
public interface UserService extends UserDetailsService {

    AppUser findUserById(Long id);

    UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException;

    List<AppUser> getAllActiveUsers(int page, int size);

    AppUser saveAppUser(UserRequest userRequest) throws Exception;

    AppUser updateUser(Long id, UserRequest userRequest);

    BaseResponse verifyUser(Long userId, String code);

    void deleteUser(Long id);

    void verifyUserBvn(Long userId, BvnVerificationRequest request);

    void verifyUserEmail(Long userId, String code);

    void saveUserIdPath(Long userId, ProfileVerificationRequest request);
}
