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

    public String createToken(Long userId, UserRole role) {
        Date issuedAt = new Date();
        Date expirationDate = new Date(issuedAt.getTime() + expirationTime);

        return JWT.create()
                .withSubject(userId.toString())
                .withClaim("role", role.toString())
                .withIssuedAt(issuedAt)
                .withExpiresAt(expirationDate)
                .sign(getAlgorithm());
    }

    public boolean validateToken(String token) {
        try {
            JWT.require(getAlgorithm()).build().verify(token);
            return true;
        } catch (TokenExpiredException e) {
            System.out.println("JWT Token Expired: " + e.getMessage());
            return false;
        } catch (JWTVerificationException e) {
            System.out.println("JWT Token Invalid: " + e.getMessage());
            return false;
        }
    }

    public String getUserIdFromToken(String token) {
        return JWT.require(getAlgorithm()).build().verify(token).getSubject();
    }
}
