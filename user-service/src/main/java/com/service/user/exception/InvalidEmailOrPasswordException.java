package com.service.user.exception;

public class InvalidEmailOrPasswordException extends RuntimeException {
    public InvalidEmailOrPasswordException() {
        super(ExceptionMessages.INVALID_EMAIL_OR_PASSWORD.getMessage());
    }
}
