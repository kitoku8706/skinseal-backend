package com.example.skin_back.user.config.jwt;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

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

    private final JwtTokenProvider jwtTokenProvider;
    
    private final CustomUserDetailsService customUserDetailsService;

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        try {
            String jwt = resolveToken(request);

            if (jwt != null) {
                // 토큰 유효성 검사
                if (jwtTokenProvider.validateToken(jwt)) {

                    String userIdStr = jwtTokenProvider.getUserIdFromToken(jwt);
                    Long userId = Long.parseLong(userIdStr);

                    UserDetails userDetails = customUserDetailsService.loadUserById(userId);

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                    );

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                
                } else {
                    // 토큰이 유효하지 않은 경우, 401 응답을 직접 작성하고 체인을 중단
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("text/plain;charset=UTF-8");
                    response.getWriter().write("JWT Token is invalid or expired.");
                    return; 
                }
            }

        } catch (Exception ex) {
            log.error("Authentication process failed: {}", ex.getMessage(), ex);

            // 예외 발생 시에도 401 응답을 강제하고 체인을 중단
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().write("Authentication failed due to token error.");
            return;
        }

        // 다음 필터로 체인 연결 (인증 성공했거나, 토큰이 없어서 건너뛴 경우)
        filterChain.doFilter(request, response);
    }
}