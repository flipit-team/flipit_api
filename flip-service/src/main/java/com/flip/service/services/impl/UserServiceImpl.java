package com.flip.service.services.impl;

import com.flip.data.entity.AppUser;
import com.flip.data.entity.AuthCode;
import com.flip.data.entity.AuthUser;
import com.flip.data.entity.UserIdentification;
import com.flip.data.enums.CodeType;
import com.flip.data.enums.ResponseCode;
import com.flip.data.enums.UserStatus;
import com.flip.data.repository.AppUserRepository;
import com.flip.data.repository.AuthUserRepository;
import com.flip.data.repository.RoleRepository;
import com.flip.data.repository.UserIdentificationRepository;
import com.flip.service.exception.SearchNotFoundException;
import com.flip.service.exception.FlipiException;
import com.flip.service.pojo.request.BvnVerificationRequest;
import com.flip.service.pojo.request.ProfileVerificationRequest;
import com.flip.service.pojo.request.UserRequest;
import com.flip.service.pojo.response.BaseResponse;
import com.flip.service.services.AuthCodeService;
import com.flip.service.services.BvnService;
import com.flip.service.services.EmailService;
import com.flip.service.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
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
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * @author Charles on 23/06/2021
 */
@Log4j2
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private BvnService bvnService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthCodeService authCodeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private UserIdentificationRepository userIdRepository;

    @Override
    public AppUser findUserById(Long id) {
        return appUserRepository.findAppUserById(id);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return authUserRepository.findAuthUserByUsername(userName);
    }

    @Override
    public List<AppUser> getAllActiveUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AppUser> userPage = appUserRepository.findAllActiveUsers(pageable);
        userPage.forEach(x -> x.getAuthUser().eraseCredentials());
        return userPage.getContent();
    }

    @Override
    @Transactional
    public AppUser saveAppUser(UserRequest userRequest) {
        if (appUserRepository.findByEmail(userRequest.getEmail()) != null ||
                authUserRepository.findAuthUserByUsername(userRequest.getEmail()) != null) {
            throw new FlipiException("A user with the email " + userRequest.getEmail() + " already exists");
        }
        if (appUserRepository.findByPhoneNumber(userRequest.getPhoneNumber()) != null) {
            throw new FlipiException("A user with the phone number " + userRequest.getPhoneNumber() + " already exists");
        }

        AuthUser authUser = new AuthUser(userRequest.getEmail(), passwordEncoder.encode(userRequest.getPassword()));
        AppUser appUser = new AppUser(authUser);
        BeanUtils.copyProperties(userRequest, appUser);
        appUser.setStatus(UserStatus.Registered);
        authUserRepository.save(authUser);
        appUserRepository.save(appUser);
        AuthCode code = authCodeService.createAuthCode(appUser, CodeType.Verification);
        emailService.sendVerificationInitiationEmail(appUser, code);
        return appUser;
    }

    @Override
    @Transactional
    public AppUser updateUser(Long id, UserRequest userRequest) {
        AppUser appUser = findUserById(id);
        if (appUser == null) {
            throw new SearchNotFoundException(AppUser.class, "id", id.toString());
        }
        AppUser emailAppUser = appUserRepository.findByEmail(userRequest.getEmail());
        if (emailAppUser != null && !emailAppUser.getId().equals(appUser.getId())) {
            throw new FlipiException("A user with the email " + userRequest.getEmail() + " already exists");
        }

        BeanUtils.copyProperties(userRequest, appUser);
        appUser.setUserRoles(new HashSet<>(roleRepository.getRolesByIdIn(userRequest.getRoleIds())));
        appUser.setDateUpdated(new Date());
        appUserRepository.save(appUser);
        return appUser;
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
        emailService.sendVerificationConfirmationEmail(appUser);
        return new BaseResponse(ResponseCode.Success);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        AppUser appUser = findUserById(id);
        if (appUser == null) {
            throw new SearchNotFoundException(AppUser.class, "id", id.toString());
        }
        appUserRepository.delete(appUser);
        emailService.sendDeactivationEmail(appUser);
    }

    @Override
    @Transactional
    public void verifyUserBvn(Long userId, BvnVerificationRequest request) {
        AppUser appUser = findUserById(userId);
        if (appUser == null)
            throw new SearchNotFoundException(AppUser.class, "id", userId.toString());

        if (StringUtils.isBlank(request.getFullName())) {
            request.setFullName(String.format("%s %s %s", appUser.getFirstName(), appUser.getMiddleName(), appUser.getLastName()));
        }
        if (!StringUtils.isNumeric(request.getBvn()) || request.getBvn().length() != 10)
            throw new FlipiException("Invalid BVN format");

        try {
            BaseResponse response = bvnService.verifyBvn(request);
            if ("00".equals(response.getResponseCode())) {
                appUser.setDateVerified(new Date());
                appUser.setBvn(request.getBvn());
                appUser.setDOB(request.getDOB());
                appUserRepository.save(appUser);
            }
        } catch (Exception ex) {
            throw new FlipiException(INTERNAL_SERVER_ERROR, "BVN verification failed. Please try again.");
        }
    }

    @Override
    @Transactional
    public void verifyUserEmail(Long userId, String code) {
        AppUser appUser = findUserById(userId);
        if (appUser == null) {
            throw new SearchNotFoundException(AppUser.class, "id", userId.toString());
        }
        AuthCode authCode = authCodeService.findAuthCode(userId, CodeType.Verification, code);
        if (authCode == null) {
            throw new FlipiException("Invalid verification link.");
        }
        appUser.setDateVerified(new Date());
        appUser.setStatus(UserStatus.Verified);
        appUserRepository.save(appUser);
        authCodeService.expireCode(authCode);
    }

    @Override
    @Transactional
    public void saveUserIdPath(Long userId, ProfileVerificationRequest request) {
        AppUser appUser = findUserById(userId);
        if (appUser == null) {
            throw new SearchNotFoundException(AppUser.class, "id", userId.toString());
        }
        UserIdentification id = userIdRepository.findUserIdentificationsByAppUser_IdAndIdType(userId, request.getIdType());
        if (id == null) {
            id = new UserIdentification();
            id.setAppUser(appUser);
        }
        id.setFilePath(request.getFilePath());
        id.setIdType(request.getIdType());
        userIdRepository.save(id);
    }
}
