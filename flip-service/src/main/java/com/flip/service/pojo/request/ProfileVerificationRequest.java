package com.flip.service.pojo.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flip.data.enums.IdentificationType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author Charles on 23/11/2022
 */
@Getter
@Setter
@ToString
@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileVerificationRequest implements Serializable {

    private String filePath;

    private IdentificationType idType;

}
