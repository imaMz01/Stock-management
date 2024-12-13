package com.service.user.exception;

public class UserAlreadyExistException extends RuntimeException {

    public UserAlreadyExistException(String email) {
        super(ExceptionMessages.UER_AlREADY_EXIST.getMessage(email));
    }
}
