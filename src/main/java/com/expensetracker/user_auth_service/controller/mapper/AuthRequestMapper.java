package com.expensetracker.user_auth_service.controller.mapper;


import com.expensetracker.user_auth_service.controller.dto.AuthRequest;
import com.expensetracker.user_auth_service.service.auth.model.LoginRequest;
import com.expensetracker.user_auth_service.service.auth.model.SignUpRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthRequestMapper {
    AuthRequestMapper INSTANCE = Mappers.getMapper(AuthRequestMapper.class);

    SignUpRequest mapToSignUpRequest(AuthRequest authRequest);

    LoginRequest mapToLoginRequest(AuthRequest authRequest);

}
