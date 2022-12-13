package com.flip.service.pojo.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Charles on 11/12/2022
 */
@Getter
@Setter
@ToString
@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class BvnVerificationRequest implements Serializable {

    private String fullName;

    @NotBlank(message = "BVN is required")
    private String bvn;

    @Required(message = "Date is required")
    private Date dOB;
}
