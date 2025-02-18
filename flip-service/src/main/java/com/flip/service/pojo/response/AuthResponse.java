package com.flip.service.pojo.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author Charles on 26/11/2021
 */
@Getter
@Component
@NoArgsConstructor
public class AuthResponse implements Serializable {

    private String jwt;

    public AuthResponse(String jwt) {
        this.jwt = jwt;
    }

}
