package com.example.skin_back.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.skin_back.user.filter.JwtAuthenticationFilter;
import com.example.skin_back.user.util.JwtUtil;

/**
 * Spring Security 설정을 위한 구성 클래스입니다.
 * 모든 요청에 대해 CSRF, HTTP Basic, 폼 로그인을 비활성화하고, 접근을 허용(permitAll)합니다.
 * 이는 JWT 등을 사용하여 인증을 처리하는 REST API 서버 환경에 적합합니다.
 */
@Configuration
public class SecurityConfig {
    @Autowired
    private JwtUtil jwtUtil;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 1. CSRF (Cross-Site Request Forgery) 보호 비활성화: 
            // REST API에서는 세션 기반 인증을 사용하지 않으므로 비활성화합니다.
            .csrf().disable()
            
            // 2. HTTP Basic 인증 비활성화: 
            // 브라우저의 강제 로그인 팝업 창(이전에 겪었던 현상)이 뜨는 것을 방지합니다.
            .httpBasic().disable() 
            
            // 3. 기본 폼 로그인 비활성화:
            // Spring Security가 제공하는 기본 로그인 페이지를 사용하지 않습니다.
            .formLogin().disable()
            
            // 4. 세션 관리 정책 설정: JWT 등을 사용할 경우 STATELESS로 설정합니다.
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            // 5. JWT 인증 필터 추가
            .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
            
            // 6. 요청별 접근 권한 설정
            .authorizeHttpRequests((authz) -> authz
                .requestMatchers(HttpMethod.POST, "/api/user").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/user/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/user/check-email").permitAll()
                .anyRequest().authenticated()
            );
            
        return http.build();
    }
}