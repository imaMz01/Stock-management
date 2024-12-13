package com.service.user.exception;

public class UserNotFundException extends RuntimeException {

    public UserNotFundException(String email) {
        super(ExceptionMessages.User_NOT_FOUND.getMessage(email));
    }
}
