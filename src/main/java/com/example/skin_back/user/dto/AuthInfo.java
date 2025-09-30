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
	private String username;
	private String password;
	private UserRole role;
	private String email;
	private String phoneNumber;
	
	public AuthInfo() {
		
	}

	public AuthInfo(Long userId, String username, String password, UserRole role, String email, String phoneNumber) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.role = role;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}
		
}