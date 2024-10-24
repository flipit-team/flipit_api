package com.flip.service.pojo.response;

import lombok.Getter;

import org.springframework.stereotype.Component;

/**
 * @author Charles on 26/11/2021
 */
@Component
public class AuthResponse {

    @Getter
    private String jwt;

    public AuthResponse(String jwt) {
        this.jwt = jwt;
    }

}
