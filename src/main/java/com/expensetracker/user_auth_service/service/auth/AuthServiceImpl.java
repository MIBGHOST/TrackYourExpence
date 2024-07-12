package com.expensetracker.user_auth_service.service.auth;

import com.expensetracker.user_auth_service.constants.ErrorMessages;
import com.expensetracker.user_auth_service.constants.LoggingConstants;
import com.expensetracker.user_auth_service.data.model.AppUser;
import com.expensetracker.user_auth_service.data.repository.AppUserRepository;
import com.expensetracker.user_auth_service.exception.BadCredentialsException;
import com.expensetracker.user_auth_service.exception.InvalidTokenException;
import com.expensetracker.user_auth_service.exception.UserAlreadyExistsException;
import com.expensetracker.user_auth_service.exception.UserNotFoundException;
import com.expensetracker.user_auth_service.service.auth.model.LoginRequest;
import com.expensetracker.user_auth_service.service.auth.model.SignUpRequest;
import com.expensetracker.user_auth_service.utils.JwtUtils;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService{
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String signUp(SignUpRequest signUpRequest) {
        var methodName = "AuthServiceImpl: signUp";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, signUpRequest);

        //check whether user already exists or not using email.
        if(appUserRepository.existsByEmail(signUpRequest.getEmail())){
            log.error(LoggingConstants.ERROR_METHOD_LOG, methodName, signUpRequest.getEmail() + "already exists!");
            throw new UserAlreadyExistsException(
                    ErrorMessages.USER_ALREADY_EXISTS.getErrorMessage(),
                    ErrorMessages.USER_ALREADY_EXISTS.getErrorCode()
            );
        }
        //create app user model
        var appUser = AppUser.builder()
                .name(signUpRequest.getName())
                        .email(signUpRequest.getEmail())
                                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .build();

        //save user
        appUserRepository.save(appUser);

        //generate token
        var accessToken = JwtUtils
                .generateAccessToken(signUpRequest.getEmail());


        log.info(LoggingConstants.END_METHOD_LOG, methodName);

        //return access token
        return accessToken;
    }

    @Override
    public String logIn(LoginRequest loginRequest) {
        var methodName = "AuthServiceImpl: logIn";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, loginRequest);

        //find user by email
        var appUser = appUserRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(()->{
                    log.error(LoggingConstants.ERROR_METHOD_LOG, methodName, loginRequest.getEmail() + " not found");

                    return new UserNotFoundException(
                            ErrorMessages.USER_NOT_FOUND.getErrorMessage(),
                            ErrorMessages.USER_NOT_FOUND.getErrorCode()
                    );
                });

        //check password
        if(!passwordEncoder.matches(loginRequest.getPassword(), appUser.getPassword())){
            log.error(LoggingConstants.ERROR_METHOD_LOG, methodName, " Incorrect Password ");

            throw new BadCredentialsException(
                    ErrorMessages.PASSWORD_NOT_MATCHED.getErrorMessage(),
                    ErrorMessages.PASSWORD_NOT_MATCHED.getErrorCode()
            );
        }

        //generate access token
        var accessToken = JwtUtils
                .generateAccessToken(loginRequest.getEmail());

        log.info(LoggingConstants.END_METHOD_LOG, methodName);

        //return access token
        return accessToken;
    }

    @Override
    public String verifyToken(String accessToken) {

        var methodName = "AuthServiceImpl: verifyToken";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, accessToken);

        //Extract username from access token
        var username = JwtUtils.getUsernameFromToken(accessToken)
                .orElseThrow(()-> {
                    log.error(LoggingConstants.END_METHOD_LOG, methodName, "Invalid Token");

                    return new InvalidTokenException(
                            ErrorMessages.INVALID_ACCESS_TOKEN.getErrorMessage(),
                            ErrorMessages.USER_NOT_FOUND.getErrorCode()
                    );
                });

        //find user by email
        var appUser = appUserRepository.findByEmail(username)
                .orElseThrow(()->{
                    log.error(LoggingConstants.ERROR_METHOD_LOG, methodName, username + " Not Found");

                    return new UserNotFoundException(
                            ErrorMessages.USER_NOT_FOUND.getErrorMessage(),
                            ErrorMessages.USER_NOT_FOUND.getErrorCode()
                    );
                });

        //return userId
        var userId = appUser.getUserId();

        log.info(LoggingConstants.END_METHOD_LOG, methodName);

        return userId;
    }
}
