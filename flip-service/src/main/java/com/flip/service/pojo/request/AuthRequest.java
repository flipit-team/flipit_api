package com.flip.service.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Charles on 26/11/2021
 */
@Getter
@Setter
@Component
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest implements Serializable {

    @NotBlank(message = "Email is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

}
