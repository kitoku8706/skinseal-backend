package com.example.skin_back.user.config.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.skin_back.user.constant.UserRole;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret-key}")
    private String secretKey;
    
    @Value("${jwt.expiration-time}")
    private long expirationTime; 

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secretKey);
    }

    // 토큰 생성
    public String createToken(Long userId, UserRole role) {
        // 토큰 발급 시간
        Date issuedAt = new Date();
        // 토큰 만료 시간
        Date expirationDate = new Date(issuedAt.getTime() + expirationTime);

        return JWT.create()
                .withSubject(userId.toString()) 
                .withClaim("role", role.toString()) 
                .withIssuedAt(issuedAt) 
                .withExpiresAt(expirationDate) 
                .sign(getAlgorithm()); 
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            JWT.require(getAlgorithm()).build().verify(token);
            return true;
        } catch (TokenExpiredException e) {
            // 토큰 만료
            System.out.println("JWT Token Expired: " + e.getMessage());
            return false;
        } catch (JWTVerificationException e) {
            // 유효하지 않은 서명
            System.out.println("JWT Token Invalid: " + e.getMessage());
            return false;
        }
    }
}
