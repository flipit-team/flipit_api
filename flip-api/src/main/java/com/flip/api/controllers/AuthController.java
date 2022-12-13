package com.flip.api.controllers;

import com.flip.data.enums.ResponseCode;
import com.flip.service.pojo.request.AuthRequest;
import com.flip.service.pojo.response.AuthResponse;
import com.flip.service.pojo.response.BaseResponse;
import com.flip.service.services.UserService;
import com.flip.service.util.JwtUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * @author Charles on 21/06/2021
 */
@Log4j2
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authManager;

    @PostMapping("/authenticate")
    public AuthResponse createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            var auth = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
            authManager.authenticate(auth);
        } catch(BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password");
        } catch (CredentialsExpiredException ignored) {}

        final UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        return new AuthResponse(jwt);
    }

    @GetMapping("/verify/{userId}/{code}")
    public ResponseEntity<BaseResponse> verifyUser(@PathVariable("userId") Long userId,
                                                   @PathVariable("code") String code) {
        try {
            BaseResponse response = userService.verifyUser(userId, code);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(ResponseCode.Internal_Server_Error),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

