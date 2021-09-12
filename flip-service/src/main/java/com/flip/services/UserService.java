package com.flip.services;

import com.flip.data.entity.User;
import com.flip.pojo.request.UserRequest;
import com.flip.pojo.response.BaseResponse;

import java.util.List;

/**
 * @author Charles on 23/06/2021
 */
public interface UserService {

    User findUserById(Long id);

    List<User> getAllActiveUsers(int page, int size);

    BaseResponse saveUser(UserRequest userRequest);

    BaseResponse updateUser(Long id, UserRequest userRequest);

    BaseResponse verifyUser(String code);

    BaseResponse deactivateUser(Long id);
}
