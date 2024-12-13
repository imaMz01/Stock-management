package com.service.user.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ExceptionMessages {

    INVALID_EMAIL_OR_PASSWORD("Invalid email or password"),
    User_NOT_FOUND("User not found with %s "),
    UER_AlREADY_EXIST("User with email %s already exist");

    private final String message;


    public String getMessage() {
        return message;
    }

    public String getMessage(Object... args) {
        return String.format(message, args);
    }
}