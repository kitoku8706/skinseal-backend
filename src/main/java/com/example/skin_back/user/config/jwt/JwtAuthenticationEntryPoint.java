package com.example.skin_back.user.config.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) 
            throws IOException {
        // 인증 실패 시 401 Unauthorized 응답을 직접 작성
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: 인증 정보가 유효하지 않습니다.");
    }
}