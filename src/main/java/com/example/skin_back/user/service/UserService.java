package com.example.skin_back.user.service;

import com.example.skin_back.user.dto.AuthInfo;
import com.example.skin_back.user.dto.UserDTO;

public interface UserService {
	public AuthInfo addMemberProcess(UserDTO dto);
	public AuthInfo loginProcess(UserDTO dto);
	
}
