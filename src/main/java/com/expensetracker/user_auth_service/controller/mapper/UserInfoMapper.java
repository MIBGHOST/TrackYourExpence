package com.expensetracker.user_auth_service.controller.mapper;


import com.expensetracker.user_auth_service.controller.dto.AuthRequest;
import com.expensetracker.user_auth_service.controller.dto.UserInfo;
import com.expensetracker.user_auth_service.data.model.AppUser;
import com.expensetracker.user_auth_service.service.auth.model.LoginRequest;
import com.expensetracker.user_auth_service.service.auth.model.SignUpRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserInfoMapper {
    UserInfoMapper INSTANCE = Mappers.getMapper(UserInfoMapper.class);

    UserInfo mapToUserInfo(AppUser appUser);

}
