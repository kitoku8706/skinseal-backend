package com.example.skin_back.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.skin_back.user.dto.AuthInfo;
import com.example.skin_back.user.dto.UserDTO;
import com.example.skin_back.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public AuthInfo addMemberProcess(UserDTO dto) {
		userRepository.save(dto.toEntity());
		return new AuthInfo(dto.getUserId(), dto.getUsername(), dto.getPassword(), dto.getRole(), dto.getEmail(), dto.getPhoneNumber());
	}
	
	@Override
	public AuthInfo loginProcess(UserDTO dto) {
	
		return null;
	}
	
	public UserServiceImpl() {

	}
}
