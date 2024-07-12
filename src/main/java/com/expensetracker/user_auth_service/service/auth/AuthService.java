package com.expensetracker.user_auth_service.service.auth;

import com.expensetracker.user_auth_service.service.auth.model.LoginRequest;
import com.expensetracker.user_auth_service.service.auth.model.SignUpRequest;

public interface AuthService {
    String signUp(SignUpRequest signUpRequest);

    String logIn(LoginRequest loginRequest);

    String verifyToken(String accessToken);
}
