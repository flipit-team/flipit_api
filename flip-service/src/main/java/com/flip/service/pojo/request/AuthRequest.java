package com.flip.service.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * @author Charles on 26/11/2021
 */
@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {

    private String username;
    private String password;

}
