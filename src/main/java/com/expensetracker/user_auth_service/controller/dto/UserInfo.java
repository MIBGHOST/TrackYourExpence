package com.expensetracker.user_auth_service.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfo {
    private String name;
    private String email;
    private String createdAt;
}
