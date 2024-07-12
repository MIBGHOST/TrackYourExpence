package com.expensetracker.user_auth_service.service.user;


import com.expensetracker.user_auth_service.data.model.AppUser;

public interface UserService {
    AppUser getUserInfo(String userId);

    void changePassword(String userId, String oldPassword, String newPassword);

    AppUser updateName(String userId, String name);


    AppUser updateEmail(String userId, String email);

}
