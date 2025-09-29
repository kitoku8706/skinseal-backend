package com.example.skin_back.user.dto;

public class LoginResponse {
    public boolean success;
    public String message;
    public String userId;
    public String role;
    public String token;
    public LoginResponse(boolean success, String message, String userId, String role, String token) {
        this.success = success;
        this.message = message;
        this.userId = userId;
        this.role = role;
        this.token = token;
    }
}