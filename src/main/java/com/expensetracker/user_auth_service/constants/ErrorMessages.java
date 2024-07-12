package com.expensetracker.user_auth_service.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorMessages {
    USER_ALREADY_EXISTS("E409", "user already exists!"),
    USER_NOT_FOUND("E404", "user not found!"),
    PASSWORD_NOT_MATCHED("E401", "Password not matched!"),
    INVALID_ACCESS_TOKEN("T401", "Invalid access Token!");

    final String errorCode;
    final String errorMessage;
}
