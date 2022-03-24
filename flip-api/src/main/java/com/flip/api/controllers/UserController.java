package com.flip.api.controllers;

import com.flip.data.entity.AppUser;
import com.flip.data.enums.ResponseCode;
import com.flip.service.pojo.request.UserRequest;
import com.flip.service.pojo.response.BaseResponse;
import com.flip.service.services.UserService;
import com.flip.service.util.ErrorUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Charles on 21/06/2021
 */
@Log4j2
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${page.size}")
    private int pageSize;

    @GetMapping("/users/{page}/{size}")
    public List<AppUser> getUsers(@PathVariable("page") int page,
                                  @PathVariable("size") int size){
        size = size == 0 ? pageSize : size;
        return userService.getAllActiveUsers(page, size);
    }

    @PostMapping("/user/signup")
    public ResponseEntity<BaseResponse> saveUser(@Valid @RequestBody UserRequest userRequest,
                                                 Errors errors) {
        BaseResponse response;
        if(errors.hasErrors()) {
            response = new BaseResponse(ResponseCode.Bad_Request);
            response.setResponseMessage(ErrorUtil.getResponseMessage(errors));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            response = userService.saveAppUser(userRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(ResponseCode.Internal_Server_Error), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<BaseResponse> updateUser(@RequestBody UserRequest userRequest,
                                                   @PathVariable("id") Long id) {
        try {
            BaseResponse response = userService.updateUser(id, userRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(ResponseCode.Internal_Server_Error), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<BaseResponse> deleteUser(@PathVariable("id") Long id){
        try {
            BaseResponse response = userService.deactivateUser(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(ResponseCode.Internal_Server_Error), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
