package com.example.skin_back.user.dto;

import com.example.skin_back.user.constant.UserRole;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class AuthInfo {
	private Long userId;
	private String loginId; 
	private String username;
	private String password;
	private String email;
	private String phoneNumber;
	private UserRole role;
	
	public AuthInfo() {
		
	}
    
	public AuthInfo(Long userId, String loginId, String username, String password, String email, String phoneNumber, UserRole role) {
		super();
		this.userId = userId;
		this.loginId = loginId; 
		this.username = username;
		this.password = password;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.role = role;
	}
		
}