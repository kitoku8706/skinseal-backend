package com.example.skin_back.user.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.skin_back.user.config.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		
		// 개발 환경에서는 모든 오리진과 HTTP 메서드를 허용 (React 앱의 접근 허용)
		configuration.setAllowedOrigins(List.of("*")); // 모든 오리진 허용
		configuration.setAllowedMethods(List.of("*")); // GET, POST 등 모든 메서드 허용
		configuration.setAllowedHeaders(List.of("*")); // 모든 헤더 허용 (JWT 토큰 포함)
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대해 적용
		return source;
	}
	
	@Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
        http
            // 1. CORS 설정 허용 (Frontend와 Backend 분리 시 필수)
            .cors(Customizer.withDefaults()) // 실제 프로젝트에서는 Customizer.withDefaults()로 CORS 설정을 별도로 합니다.
            
            // 2. CSRF 보호 기능 비활성화 (JWT 사용 시 일반적으로 비활성화)
            .csrf(AbstractHttpConfigurer::disable)
            
            // 3. HTTP Basic 인증 비활성화
            .httpBasic(AbstractHttpConfigurer::disable)
            
            // 4. 세션 비활성화 (JWT는 무상태(Stateless)를 지향)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // 5. 요청별 인가(Authorization) 설정
            .authorizeHttpRequests(authorize -> authorize
                // 회원가입 및 로그인 요청은 인증 없이 허용 (토큰 발급 경로)
                .requestMatchers("/member/signup", "/member/login", "/member/user").permitAll()
                // Swagger UI 경로도 허용 (API 테스트 용도)
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                // 공지사항 경로 허용
                .requestMatchers("/api/notice/**").permitAll()
                // 그 외 모든 요청은 인증(토큰 검증)을 요구
                .anyRequest().authenticated()
            );

        // 6. 💥 JWT 필터를 Spring Security 필터 체인에 추가
        // UsernamePasswordAuthenticationFilter 이전에 커스텀 JWT 필터를 삽입하여 
        // 토큰 유효성 검사를 먼저 수행하도록 합니다.
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
	
}