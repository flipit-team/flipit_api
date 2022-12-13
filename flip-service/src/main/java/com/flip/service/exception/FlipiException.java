package com.flip.service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author Charles on 13/12/2022
 */
public class FlipiException extends RuntimeException {

    @Getter
    private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public FlipiException(String message) {
        super(message);
    }

    public FlipiException(HttpStatus status, String message) {
        super(message);
        httpStatus = status;
    }

}
