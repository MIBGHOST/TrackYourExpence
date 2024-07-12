package com.expensetracker.user_auth_service.controller;

import com.expensetracker.user_auth_service.constants.LoggingConstants;
import com.expensetracker.user_auth_service.controller.dto.AuthRequest;
import com.expensetracker.user_auth_service.controller.dto.AuthResponse;
import com.expensetracker.user_auth_service.controller.dto.VerifyTokenRequest;
import com.expensetracker.user_auth_service.controller.dto.VerifyTokenResponse;
import com.expensetracker.user_auth_service.controller.mapper.AuthRequestMapper;
import com.expensetracker.user_auth_service.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    //signUp
    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> signUp(@RequestBody AuthRequest authRequest){
        var methodName = "AuthController: signUp";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, authRequest);

        var accessToken = authService.signUp(
                AuthRequestMapper.INSTANCE.mapToSignUpRequest(authRequest)
        );

        log.info(LoggingConstants.END_METHOD_LOG, methodName);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        AuthResponse.builder()
                                .accessToken(accessToken)
                                .build()
                );
    }
    //logIn
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> logIn(@RequestBody AuthRequest authRequest){
        var methodName = "AuthController: logIn";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, authRequest);

        var accessToken = authService.logIn(
                AuthRequestMapper.INSTANCE.mapToLoginRequest(authRequest)
        );

        log.info(LoggingConstants.END_METHOD_LOG, methodName);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        AuthResponse.builder()
                                .accessToken(accessToken)
                                .build()
                );
    }
    //verifyToken
    @PostMapping("/verify-token")
    public ResponseEntity<VerifyTokenResponse> verifyToken(@RequestBody VerifyTokenRequest verifyTokenRequest){
        var methodName = "AuthController: verifyToken";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, verifyTokenRequest);

        var userId = authService.verifyToken(verifyTokenRequest.getAccessToken());

        log.info(LoggingConstants.END_METHOD_LOG, methodName);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        VerifyTokenResponse.builder()
                                .userId(userId)
                                .build()
                );
    }

}
