package com.flip.pojo.response;

import com.flip.enums.ResponseCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author Charles on 01/07/2021
 */
@Getter
@Setter
@ToString
@Component
public class BaseResponse implements Serializable {

    private String responseCode;
    private String responseMessage;

    public BaseResponse(){}

    public BaseResponse(String responseCode, String responseMessage){
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public BaseResponse(ResponseCode responseCode){
        this.responseCode = responseCode.getCode();
        this.responseMessage = responseCode.getMessage();
    }
}