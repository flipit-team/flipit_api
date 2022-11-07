package com.flip.api.controllers;

import com.flip.data.entity.AppUser;
import com.flip.data.enums.ResponseCode;
import com.flip.service.pojo.request.UserRequest;
import com.flip.service.pojo.response.BaseResponse;
import com.flip.service.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{page}/{size}")
    public List<AppUser> getUsers(@PathVariable("page") int page,
                                  @PathVariable("size") int size) {
        size = size == 0 ? pageSize : size;
        return userService.getAllActiveUsers(page, size);
    }

    @PostMapping("/signup")
    public AppUser saveUser(@Valid @RequestBody UserRequest userRequest) throws Exception {
        return userService.saveAppUser(userRequest);
    }

    @PutMapping("/{id}")
    public AppUser updateUser(@RequestBody UserRequest userRequest, @PathVariable("id") Long id) {
        return userService.updateUser(id, userRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteUser(@PathVariable("id") Long id) {
        try {
            BaseResponse response = userService.deactivateUser(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(ResponseCode.Internal_Server_Error), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
