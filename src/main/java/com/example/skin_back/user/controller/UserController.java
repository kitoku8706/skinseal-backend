package com.example.skin_back.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.skin_back.user.dto.AuthInfo;
import com.example.skin_back.user.dto.AuthResponseDTO;
import com.example.skin_back.user.dto.UserDTO;
import com.example.skin_back.user.repository.UserRepository;
import com.example.skin_back.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/member")
public class UserController {

    private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	public UserController() {
	
	}
		
    @GetMapping("/user")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        // DB 연결 및 조회 확인을 위한 엔드포인트
        // 실제 UserService에 getAllUsers() 메서드가 구현되어 있어야 합니다.
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
	
	// 회원가입
	@PostMapping(value = "/signup")
	public ResponseEntity<AuthInfo> addMember(@RequestBody UserDTO userDTO){

		AuthInfo authInfo = userService.addMemberProcess(userDTO);
		
		return ResponseEntity.ok(authInfo);

	}
	
    // 로그인
    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody UserDTO userDTO){
    	AuthResponseDTO authResponseDTO = userService.loginProcess(userDTO);
        
        return ResponseEntity.ok(authResponseDTO); 
    }
	
}