package com.flip.service.pojo.response;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Charles on 26/11/2021
 */
@Component
public class AuthResponse {

    private final String jwt;

    public AuthResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt(){
        return jwt;
    }
}
