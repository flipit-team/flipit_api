package com.flip.service.services.impl;

import com.flip.data.entity.AppUser;
import com.flip.data.entity.AuthCode;
import com.flip.data.entity.AuthUser;
import com.flip.data.enums.CodeType;
import com.flip.data.enums.ResponseCode;
import com.flip.data.enums.UserStatus;
import com.flip.data.repository.AppUserRepository;
import com.flip.data.repository.AuthUserRepository;
import com.flip.data.repository.RoleRepository;
import com.flip.service.exception.EntityNotFoundException;
import com.flip.service.exception.UserExistsException;
import com.flip.service.pojo.request.UserRequest;
import com.flip.service.pojo.response.BaseResponse;
import com.flip.service.services.AuthCodeService;
import com.flip.service.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
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
    private AuthCodeService authCodeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUser findUserById(Long id) {
        return appUserRepository.findAppUserById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //return new User("foo", "foo", new ArrayList<>());
        AuthUser user = authUserRepository.findAuthUserByUsername(userName);
        return user;
    }

    @Override
    public List<AppUser> getAllActiveUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AppUser> userPage = appUserRepository.findAllActiveUsers(pageable);
        userPage.forEach(x ->
                x.getAuthUser().eraseCredentials());
        return userPage.getContent();
    }

    @Override
    @Transactional
    public AppUser saveAppUser(UserRequest userRequest) throws Exception {
        if (appUserRepository.findByEmail(userRequest.getEmail()) != null ||
                authUserRepository.findAuthUserByUsername(userRequest.getEmail()) != null) {
            throw new UserExistsException(userRequest.getEmail());
        }

        AuthUser authUser = new AuthUser(userRequest.getEmail(), passwordEncoder.encode(userRequest.getPassword()));
        AppUser appUser = new AppUser(authUser);
        BeanUtils.copyProperties(userRequest, appUser);
        appUser.setStatus(UserStatus.Registered);
        authUserRepository.save(authUser);
        appUserRepository.save(appUser);
        //emailService.sendAccountVerificationEmail(user);
        return appUser;
    }

    @Override
    public AppUser updateUser(Long id, UserRequest userRequest) {
        AppUser appUser = findUserById(id);
        if (appUser == null) {
            throw new EntityNotFoundException(AppUser.class, "id", id.toString());
        }

        AppUser emailAppUser = appUserRepository.findByEmail(userRequest.getEmail());
        if (emailAppUser != null && !emailAppUser.getId().equals(appUser.getId())) {
            throw new UserExistsException(userRequest.getEmail());
        }

        BeanUtils.copyProperties(userRequest, appUser);
        /*appUser.setTitle(userRequest.getTitle());
        appUser.setFirstName(userRequest.getFirstName());
        appUser.setMiddleName(userRequest.getMiddleName());
        appUser.setLastName(userRequest.getLastName());
        appUser.setPhoneNumber(userRequest.getPhoneNumber());*/
        appUser.setUserRoles(new HashSet<>(roleRepository.getRolesByIdIn(userRequest.getRoleIds())));
        appUserRepository.save(appUser);
        return appUser;
    }

    @Override
    public BaseResponse initiateUserVerification(Long userId) {
        AppUser appUser = appUserRepository.findAppUserById(userId);
        if (appUser == null) {
            return new BaseResponse(ResponseCode.Not_Found);
        }

        authCodeService.createAuthCode(appUser, CodeType.Verification);
        //emailService.sendVerificationInitiationEmail(user);
        return new BaseResponse(ResponseCode.Success);
    }

    @Override
    @Transactional
    public BaseResponse verifyUser(Long userId, String code) {
        AppUser appUser = appUserRepository.findAppUserById(userId);
        if (appUser == null) {
            return new BaseResponse(ResponseCode.Not_Found);
        }

        AuthCode authCode = authCodeService.findAuthCode(userId, CodeType.Verification, code);
        if (authCode == null) {
            return new BaseResponse(ResponseCode.Invalid_Code);
        }

        appUser.setStatus(UserStatus.Verified);
        appUser.setDateVerified(new Date());
        appUserRepository.save(appUser);
        authCodeService.expireCode(authCode);
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
