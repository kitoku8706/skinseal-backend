package com.example.skin_back.timetable.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class TimeTableConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        
        // ✅ React 개발 서버 주소 (CORS 허용)
        config.addAllowedOriginPattern("http://localhost:*");
        // ✅ 모든 요청 헤더 허용
        config.addAllowedHeader("*");
        
        // ✅ 모든 HTTP 메서드 허용 (GET, POST, PUT, DELETE 등)
        config.addAllowedMethod("*");
        
        // ✅ 쿠키/인증정보 허용
        config.setAllowCredentials(true);

        // ✅ 모든 경로에 대해 CORS 설정 적용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
