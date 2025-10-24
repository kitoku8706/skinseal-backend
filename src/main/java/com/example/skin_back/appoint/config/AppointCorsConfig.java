package com.example.skin_back.appoint.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ✅ 공용 AppointCorsConfig
 * - SecurityConfig와 충돌 없음
 * - React(5181) 허용
 * - 다른 API의 보안 구조 변경하지 않음
 */
@Configuration
public class AppointCorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5181") // React 포트
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true); // withCredentials:true 대응
    }
}
