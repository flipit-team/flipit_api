package com.flip.service.exception;

/**
 * @author Charles on 02/11/2022
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }

}
