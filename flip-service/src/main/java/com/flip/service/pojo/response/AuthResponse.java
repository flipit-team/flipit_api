package com.flip.service.pojo.response;

import org.springframework.stereotype.Component;

/**
 * @author Charles on 26/11/2021
 */
@Component
public class AuthResponse {

    private String jwt;

    private AuthResponse() {}

    public AuthResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt(){
        return jwt;
    }
}
