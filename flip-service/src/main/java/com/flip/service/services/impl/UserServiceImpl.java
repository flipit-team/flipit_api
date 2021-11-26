package com.flip.service.services.impl;

import com.flip.data.entity.AppUser;
import com.flip.data.entity.AuthUser;
import com.flip.data.enums.ResponseCode;
import com.flip.data.enums.UserStatus;
import com.flip.data.repository.AppUserRepository;
import com.flip.data.repository.AuthUserRepository;
import com.flip.data.repository.RoleRepository;
import com.flip.service.pojo.request.UserRequest;
import com.flip.service.pojo.response.BaseResponse;
import com.flip.service.services.UserService;
import com.flip.service.util.RefUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * @author Charles on 23/06/2021
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUser findUserById(Long id) {
        return appUserRepository.findAppUserById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return new AuthUser("foo", "foo", new ArrayList<>()); //authUserRepository.findByUsername(userName);
    }

    @Override
    public List<AppUser> getAllActiveUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AppUser> userPage = appUserRepository.findAllActiveUsers(pageable);
        userPage.forEach(x ->
                x.getAuthUser().eraseCredentials());
        List<AppUser> appUsers = userPage.getContent();
        return appUsers;
    }

    @Override
    @Transactional
    public BaseResponse saveAppUser(UserRequest userRequest) {
        if (appUserRepository.findByEmail(userRequest.getEmail()) != null ||
                authUserRepository.findByUsername(userRequest.getEmail()) != null) {
            return new BaseResponse(ResponseCode.Bad_Request.getCode(), "A user with this email already exists.");
        }
        if (authUserRepository.findByUsername(userRequest.getEmail()) != null) {
            return new BaseResponse(ResponseCode.Bad_Request.getCode(), "A user with this email already exists.");
        }

        AuthUser authUser = new AuthUser(userRequest.getEmail(), passwordEncoder.encode(userRequest.getPassword()), null);
        AppUser appUser = new AppUser(authUser);
        BeanUtils.copyProperties(userRequest, appUser);
        appUser.setStatus(UserStatus.Registered);
        authUserRepository.save(authUser);
        appUserRepository.save(appUser);
        //emailService.sendAccountVerificationEmail(user);
        return new BaseResponse(ResponseCode.Success);
    }

    @Override
    public BaseResponse updateUser(Long id, UserRequest userRequest) {
        AppUser appUser = findUserById(id);
        if (appUser == null) {
            return new BaseResponse(ResponseCode.Not_Found);
        }

        AppUser emailAppUser = appUserRepository.findByEmail(userRequest.getEmail());
        if (emailAppUser != null && !emailAppUser.getId().equals(appUser.getId())) {
            return new BaseResponse(ResponseCode.Bad_Request.getCode(), "A user with this email already exists.");
        }

        appUser.setTitle(userRequest.getTitle());
        appUser.setFirstName(userRequest.getFirstName());
        appUser.setLastName(userRequest.getLastName());
        appUser.setEmail(userRequest.getEmail());
        appUser.setPhoneNumber(userRequest.getMobile());
        appUser.setUserRoles(new HashSet<>(
                roleRepository.getRolesByIdIn(userRequest.getRoleIds())));
        appUserRepository.save(appUser);
        return new BaseResponse(ResponseCode.Success);
    }

    @Override
    public BaseResponse initiateUserVerification(Long userId) {
        AppUser appUser = appUserRepository.findAppUserById(userId);
        if (appUser == null) {
            return new BaseResponse(ResponseCode.Not_Found);
        }

        appUser.setVerificationCode(RefUtil.generateUniqueRef());
        appUserRepository.save(appUser);
        //emailService.sendVerificationInitiationEmail(user);
        return new BaseResponse(ResponseCode.Success);
    }

    @Override
    public BaseResponse verifyUser(String code) {
        AppUser appUser = appUserRepository.findUserByVerificationCode(code);
        if (appUser == null) {
            return new BaseResponse(ResponseCode.Not_Found);
        }

        appUser.setStatus(UserStatus.Verified);
        appUser.setDateVerified(new Date());
        appUserRepository.save(appUser);
        //emailService.sendVerificationConfirmationEmail(user);
        return new BaseResponse(ResponseCode.Success);
    }

    @Override
    public BaseResponse deactivateUser(Long id) {
        AppUser appUser = findUserById(id);
        if (appUser == null) {
            return new BaseResponse(ResponseCode.Not_Found);
        }

        appUser.setStatus(UserStatus.Deactivated);
        appUserRepository.save(appUser);
        //emailService.sendDeactivationEmail(user);
        return new BaseResponse(ResponseCode.Success);
    }

}
