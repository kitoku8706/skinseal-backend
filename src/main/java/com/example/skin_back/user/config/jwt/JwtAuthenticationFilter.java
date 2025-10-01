package com.example.skin_back.user.config.jwt;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.skin_back.user.service.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // 토큰 생성 및 검증 유틸리티
    private final JwtTokenProvider jwtTokenProvider; 
    
    // 토큰에서 추출한 ID로 DB에서 UserDetails를 로드하는 서비스
    // ⚠️ CustomUserDetailsService는 아직 구현하지 않았지만, 다음 단계에서 JWT와 통합하기 위해 미리 선언합니다.
    private final CustomUserDetailsService customUserDetailsService; 

    // HTTP 헤더에서 토큰을 추출하는 메서드
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        
        // "Bearer "로 시작하는지 확인하고 띄어쓰기 다음부터 순수 토큰 문자열을 반환
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        try {
            // 1. HTTP 헤더에서 JWT 토큰 추출
            String jwt = resolveToken(request);

            // 2. 토큰이 존재하고 유효한 경우
            if (jwt != null && jwtTokenProvider.validateToken(jwt)) {
                
                // 3. 토큰에서 사용자 ID(Subject) 추출 (JwtTokenProvider에 해당 로직 추가 필요)
                // 현재 JwtTokenProvider에는 토큰에서 정보를 추출하는 메서드가 없으므로, 잠시 이 부분은 가이드로 남겨둡니다.
                // 4. 추출된 사용자 ID로 UserDetails 로드
                // UserDetails userDetails = customUserDetailsService.loadUserByUsername(jwtTokenProvider.getUserIdFromToken(jwt));

                // ⚠️ 지금은 임시로 토큰이 유효하면 인증되었다고 가정합니다. 
                // 실제 UserDetails 로직은 다음 단계에서 JwtTokenProvider와 함께 보완하겠습니다.
                
                // 5. Spring Security 컨텍스트에 인증 정보 저장 (실제 인증이 이루어지는 곳)
                // UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                //     userDetails, null, userDetails.getAuthorities()
                // );
                // authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            // 토큰 관련 예외 발생 시
            logger.error("Could not set user authentication in security context", ex);
        }

        // 다음 필터로 체인 연결
        filterChain.doFilter(request, response);
    }
}

