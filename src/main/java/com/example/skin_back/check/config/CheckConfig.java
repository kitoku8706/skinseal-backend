package com.example.skin_back.check.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * ✅ timetable과 별도로 check API 전용 CORS 설정
 * ✅ Bean 이름 충돌 방지를 위해 checkCorsFilter로 명명
 */
@Configuration
public class CheckConfig {

    @Bean(name = "checkCorsFilter")
    public CorsFilter checkCorsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:5174"); // React dev
        config.addAllowedOrigin("http://98.87.24.151");   // Frontend EC2
        config.addAllowedOrigin("http://98.87.24.151:5173");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/check/**", config);
        return new CorsFilter(source);
    }
}
