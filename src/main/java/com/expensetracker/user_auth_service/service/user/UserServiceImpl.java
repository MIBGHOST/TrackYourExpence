package com.expensetracker.user_auth_service.service.user;

import com.expensetracker.user_auth_service.constants.ErrorMessages;
import com.expensetracker.user_auth_service.constants.LoggingConstants;
import com.expensetracker.user_auth_service.data.model.AppUser;
import com.expensetracker.user_auth_service.data.repository.AppUserRepository;
import com.expensetracker.user_auth_service.exception.BadCredentialsException;
import com.expensetracker.user_auth_service.exception.InvalidTokenException;
import com.expensetracker.user_auth_service.exception.UserAlreadyExistsException;
import com.expensetracker.user_auth_service.exception.UserNotFoundException;
import com.expensetracker.user_auth_service.service.auth.AuthService;
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
public class UserServiceImpl implements UserService{
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public AppUser getUserInfo(String userId) {
        var methodName = "UserServiceImpl:getUserInfo";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);

        //find user by userId
        var appuser = appUserRepository.findById(userId)
                .orElseThrow(()->{
                    log.error(LoggingConstants.ERROR_METHOD_LOG, methodName, userId + " not found");

                    return new UserNotFoundException(
                            ErrorMessages.USER_NOT_FOUND.getErrorMessage(),
                            ErrorMessages.USER_NOT_FOUND.getErrorCode()
                    );
                });

        log.info(LoggingConstants.END_METHOD_LOG, methodName);

        return appuser;
    }

    //changing the password
    @Override
    public void changePassword(String userId, String oldPassword, String newPassword) {
        var methodName = "UserServiceImpl:changePassword";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);

        //find user by userId
        var appUser = getUserInfo(userId);

        //check old Password
        if(!passwordEncoder.matches(oldPassword, appUser.getPassword())){
            log.error(LoggingConstants.ERROR_METHOD_LOG, methodName, "Incorrect Password");

            throw new BadCredentialsException(
                    ErrorMessages.PASSWORD_NOT_MATCHED.getErrorMessage(),
                    ErrorMessages.PASSWORD_NOT_MATCHED.getErrorCode()
            );
        }
        //set new Password
        appUser.setPassword(passwordEncoder.encode(newPassword));

        //save user
        appUserRepository.save(appUser);

        log.info(LoggingConstants.END_METHOD_LOG, methodName);
    }

    //updating user info
    @Override
    public AppUser updateName(String userId, String name) {
        var methodName = "UserServiceImpl:updateName";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);


        //find user by userId
        var appUser = getUserInfo(userId);

        //update name
        appUser.setName(name);

        //save user
        var savedUser = appUserRepository.save(appUser);

        log.info(LoggingConstants.END_METHOD_LOG, methodName);

        return savedUser;
    }

    @Override
    public AppUser updateEmail(String userId, String email) {
        var methodName = "UserServiceImpl:updateEmail";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);

        //find user by userId
        var appUser = getUserInfo(userId);

        //update name
        appUser.setEmail(email);

        //save user
        var savedUser = appUserRepository.save(appUser);

        log.info(LoggingConstants.END_METHOD_LOG, methodName);

        return savedUser;
    }
}
