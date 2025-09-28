package com.example.skin_back.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private Long userId;
    private String password;
    private String role;
    private String email;
    private String phoneNumber;
}