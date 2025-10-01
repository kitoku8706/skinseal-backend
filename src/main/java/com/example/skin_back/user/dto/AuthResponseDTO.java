package com.example.skin_back.user.dto;

import com.example.skin_back.user.constant.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {
    
    // JWT Access Token (클라이언트에 전달할 인증서)
    private String token;
    
    // 로그인한 사용자의 주요 정보
    private Long userId;
    private String username;
    private UserRole role;
    private String email;
    private String phoneNumber;
    

}
