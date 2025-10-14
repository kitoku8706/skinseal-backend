package com.example.skin_back.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.skin_back.user.config.CustomUserDetails;
import com.example.skin_back.user.dto.AuthInfo;
import com.example.skin_back.user.dto.AuthResponseDTO;
import com.example.skin_back.user.dto.MemberInfoDTO;
import com.example.skin_back.user.dto.MemberUpdateDTO;
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
    
    private String getAuthenticatedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("인증된 사용자 정보가 없습니다.");
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            return ((CustomUserDetails) principal).getUserEntity().getUsername();
        }
        return ((UserDetails) principal).getUsername();
    }
    
    @GetMapping("/info")
    public ResponseEntity<MemberInfoDTO> getUserInfo() {
        String username = getAuthenticatedUsername();
        MemberInfoDTO userInfo = userService.getUserInfo(username);
        return ResponseEntity.ok(userInfo);
    }
    
    @PutMapping("/update")
    public ResponseEntity<String> updateMember(@RequestBody MemberUpdateDTO updateDto) {
        try {
            String username = getAuthenticatedUsername();
            userService.updateMemberInfo(username, updateDto);
            
            return ResponseEntity.ok("회원정보가 성공적으로 수정되었습니다.");
        } catch (RuntimeException e) {
            log.error("회원정보 수정 실패: {}", e.getMessage());
            // 비밀번호 불일치 등 사용자 정의 예외 처리
            return ResponseEntity.badRequest().body("정보 수정 실패: " + e.getMessage());
        }
    }
	
}