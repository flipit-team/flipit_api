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
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/verify/{code}")
    public ResponseEntity<BaseResponse> verifyUser(@PathVariable("code") String code) {
        try {
            BaseResponse response = userService.verifyUser(code);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(ResponseCode.Internal_Server_Error), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
