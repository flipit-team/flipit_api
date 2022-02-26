package com.flip.data.enums;

import lombok.Getter;

/**
 * @author Charles on 01/07/2021
 */
@Getter
public enum ResponseCode {

    Success("00", "Successful"),
    Not_Found("22", "Requested Resource Not Found"),
    Bad_Request("33", "Bad/Invalid Request"),
    Invalid_Code("50", "The code you entered is invalid/expired."),
    Internal_Server_Error("ZZ", "Something went wrong. Please try again later."),
    ;

    private String code;
    private String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}