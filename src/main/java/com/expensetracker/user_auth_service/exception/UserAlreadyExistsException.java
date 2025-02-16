package com.expensetracker.user_auth_service.exception;

import lombok.Getter;

@Getter
public class UserAlreadyExistsException extends RuntimeException {
    private final String errorCode;

    public UserAlreadyExistsException(String message, String errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
