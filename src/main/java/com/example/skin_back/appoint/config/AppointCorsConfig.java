package com.example.skin_back.appoint.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ✅ 안정 버전 AppointCorsConfig
 * - JwtTokenProvider, SecurityConfig, Filter 전혀 수정하지 않아도 됨
 * - React(5173, 5181) 기반만 허용
 */
@Configuration
public class AppointCorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:5173",
                        "http://localhost:5181"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true); // ✅ JWT 쿠키 or 헤더 전달 가능
    }
}
