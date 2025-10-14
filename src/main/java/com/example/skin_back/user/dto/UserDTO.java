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
	private String loginId;
	private String username;
	private String password;
	private String birth;
	private String email;
	private String phoneNumber;
	private UserRole role;
	
	public UserEntity toEntity() {
		return UserEntity.builder().userId(userId).loginId(loginId).username(username)
				.password(password).birth(birth).email(email).phoneNumber(phoneNumber).role(role).build();
	}
	
	public static UserDTO toDTO(UserEntity membersEntity) {
		return UserDTO.builder().userId(membersEntity.getUserId()).loginId(membersEntity.getLoginId()).username(membersEntity.getUsername())
				.password(membersEntity.getPassword()).birth(membersEntity.getBirth()).email(membersEntity.getEmail())
				.phoneNumber(membersEntity.getPhoneNumber()).role(membersEntity.getRole()).build();
	}
	
}
