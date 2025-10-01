package com.example.skin_back.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.skin_back.user.dto.AuthInfo;
import com.example.skin_back.user.dto.AuthResponseDTO;
import com.example.skin_back.user.dto.UserDTO;
import com.example.skin_back.user.repository.UserRepository;
import com.example.skin_back.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UserController {

    private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	public UserController() {
	
	}
		
	// 회원가입
	@PostMapping(value = "/member/signup")
	public ResponseEntity<AuthInfo> addMember(@RequestBody UserDTO userDTO){

		AuthInfo authInfo = userService.addMemberProcess(userDTO);
		
		return ResponseEntity.ok(authInfo);

	}
	
    // 로그인
    @PostMapping(value = "/member/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody UserDTO userDTO){
    	AuthResponseDTO authResponseDTO = userService.loginProcess(userDTO);
        
        return ResponseEntity.ok(authResponseDTO); 
    }
	
}