package com.example.skin_back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring Security 설정을 위한 구성 클래스입니다.
 * JWT 토큰 기반 인증을 사용하는 REST API 서버 환경에 적합하도록 설정합니다.
 */
@Configuration
public class SecurityConfig {
    
    /**
     * 핵심 보안 필터 체인(Security Filter Chain)을 정의합니다.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 1. JWT 기반이므로 CSRF, HTTP Basic, 폼 로그인 비활성화
            // AbstractHttpConfigurer::disable 는 최신 Spring Security 6+에서 권장되는 비활성화 방법입니다.
            .csrf(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable) 
            .formLogin(AbstractHttpConfigurer::disable)
            
            // 2. 세션 정책을 STATELESS로 설정 (토큰 인증을 사용)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            
            // 3. 요청별 접근 권한 설정
            .authorizeHttpRequests(authorize -> authorize
                // 회원가입, 로그인, 이메일 확인, 공지사항 조회는 인증 없이 허용 (permitAll)
                .requestMatchers(HttpMethod.POST, "/api/user").permitAll() // 회원가입
                .requestMatchers(HttpMethod.POST, "/api/user/login").permitAll() // 로그인
                .requestMatchers(HttpMethod.GET, "/api/user/check-email").permitAll() // 이메일 중복 확인
                .requestMatchers(HttpMethod.GET, "/api/notice").permitAll() // 공지사항 목록 조회
                
                // 위를 제외한 나머지 모든 요청은 반드시 인증 필요 (authenticated)
                .anyRequest().authenticated()
            );
            
        return http.build();
    }

    /**
     * 비밀번호 암호화를 위한 BCryptPasswordEncoder Bean을 등록합니다.
     * 이 Bean은 사용자 비밀번호를 저장하거나 검증할 때 사용됩니다.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
