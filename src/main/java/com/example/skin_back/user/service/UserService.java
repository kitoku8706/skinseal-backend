package com.example.skin_back.user.service;

import java.util.List;

import com.example.skin_back.user.dto.AuthInfo;
import com.example.skin_back.user.dto.AuthResponseDTO;
import com.example.skin_back.user.dto.UserDTO;

public interface UserService {
	public AuthInfo addMemberProcess(UserDTO dto);
	public AuthResponseDTO loginProcess(UserDTO dto);
	List<UserDTO> getAllUsers();
}
