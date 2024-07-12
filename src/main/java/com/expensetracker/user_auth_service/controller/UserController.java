package com.expensetracker.user_auth_service.controller;

import com.expensetracker.user_auth_service.constants.LoggingConstants;
import com.expensetracker.user_auth_service.controller.dto.ChangePasswordRequest;
import com.expensetracker.user_auth_service.controller.dto.UserDetails;
import com.expensetracker.user_auth_service.controller.dto.UserInfo;
import com.expensetracker.user_auth_service.controller.mapper.UserInfoMapper;
import com.expensetracker.user_auth_service.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    //get user info
    @GetMapping("/{userId}")
    public ResponseEntity<UserInfo> getUserInfo(@PathVariable String userId){
        var methodName = "UserController:getUserInfo";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);

        var appUser = userService.getUserInfo(userId);

        log.info(LoggingConstants.END_METHOD_LOG, methodName);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        UserInfoMapper.INSTANCE.mapToUserInfo(appUser)
                );
    }

    //change Password
    @PostMapping("/{userId}/change-password")
    public ResponseEntity<Void> changePassword(@PathVariable String userId,
                               @RequestBody ChangePasswordRequest changePasswordRequest)
    {

        var methodName = "UserController:changePassword";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);

        userService.changePassword(
                userId,
                changePasswordRequest.getOldPassword(),
                changePasswordRequest.getNewPassword()
        );

        log.info(LoggingConstants.END_METHOD_LOG, methodName);

        return ResponseEntity
                .ok()
                .build();

    }
    //update user info
    @PostMapping("/{userId}/update-user")
    public  ResponseEntity<UserInfo> updateUserInfo(@PathVariable String userId,
                               @RequestBody UserDetails userDetails)
    {

        var methodName = "UserController:updateUserInfo";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);

        var appUser = switch (userDetails.getRequestType()){
            case UPDATE_NAME -> userService.updateName(userId, userDetails.getName());
            case UPDATE_EMAIL -> userService.updateEmail(userId, userDetails.getEmail());
        };

        log.info(LoggingConstants.END_METHOD_LOG, methodName);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        UserInfoMapper.INSTANCE.mapToUserInfo(appUser)
                );
    }


}
