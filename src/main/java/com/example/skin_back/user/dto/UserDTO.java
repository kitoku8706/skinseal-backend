package com.example.skin_back.user.dto;

import com.example.skin_back.user.constant.UserRole;
import com.example.skin_back.user.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class UserDTO {
	private Long userId;
	private String username;
	private String password;
	private UserRole role;
	private String email;
	private String phoneNumber;
	
	public UserEntity toEntity() {
		return UserEntity.builder().userId(userId).username(username)
				.password(password).role(role).email(email).phoneNumber(phoneNumber).build();
	}
	
	public static UserEntity toDTO(UserEntity membersEntity) {
		return UserEntity.builder().userId(membersEntity.getUserId()).username(membersEntity.getUsername())
				.password(membersEntity.getPassword()).role(membersEntity.getRole()).email(membersEntity.getEmail())
				.phoneNumber(membersEntity.getPhoneNumber()).build();
	}
	
}
