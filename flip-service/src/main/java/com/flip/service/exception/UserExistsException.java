package com.flip.service.exception;

/**
 * @author Charles on 02/11/2022
 */
public class UserExistsException extends RuntimeException {

    public UserExistsException(String email) {
        super(String.format("A user with the email %s already exist", email));
    }

}
