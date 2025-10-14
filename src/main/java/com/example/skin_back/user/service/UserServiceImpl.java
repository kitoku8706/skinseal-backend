package com.example.skin_back.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.skin_back.user.config.jwt.JwtTokenProvider;
import com.example.skin_back.user.constant.UserRole;
import com.example.skin_back.user.dto.AuthInfo;
import com.example.skin_back.user.dto.AuthResponseDTO;
import com.example.skin_back.user.dto.MemberInfoDTO;
import com.example.skin_back.user.dto.MemberUpdateDTO;
import com.example.skin_back.user.dto.UserDTO;
import com.example.skin_back.user.entity.UserEntity;
import com.example.skin_back.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Override
	public AuthInfo addMemberProcess(UserDTO dto) {

		if (userRepository.existsByUsername(dto.getUsername())) {
			// 중복 아이디 처리 로직 추가 예정
			throw new RuntimeException("이미 존재하는 사용자 이름입니다.");
		}

		dto.setRole(UserRole.USER);

		String encodedPassword = passwordEncoder.encode(dto.getPassword());
		dto.setPassword(encodedPassword);

		userRepository.save(dto.toEntity());

		return new AuthInfo(dto.getUserId(), dto.getUsername(), null, dto.getRole(), dto.getEmail(),
				dto.getPhoneNumber());
	}

	@Override
	public AuthResponseDTO loginProcess(UserDTO dto) {
		Optional<UserEntity> userOptional = userRepository.findByUsername(dto.getUsername());

		if (userOptional.isEmpty()) {
			// 사용자 없음 처리 로직 추가 예정
			throw new RuntimeException("사용자를 찾을 수 없습니다.");
		}

		UserEntity userEntity = userOptional.get();

		if (!passwordEncoder.matches(dto.getPassword(), userEntity.getPassword())) {
			// 비밀번호 불일치 로직 추가 예정
			throw new RuntimeException("비밀번호가 일치하지 않습니다.");
		}

		String token = jwtTokenProvider.createToken(userEntity.getUserId(), userEntity.getRole());

		return AuthResponseDTO.builder().token(token).userId(userEntity.getUserId()).username(userEntity.getUsername())
				.role(userEntity.getRole()).email(userEntity.getEmail()).phoneNumber(userEntity.getPhoneNumber())
				.build();
	}
	
	@Override
	public List<UserDTO> getAllUsers() {
		
		return List.of();
	}
	
	@Override
	public MemberInfoDTO getUserInfo(String username) {
	    UserEntity userEntity = userRepository.findByUsername(username)
	            .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

	    return MemberInfoDTO.fromEntity(userEntity);
	}
	
	@Override
	public void updateMemberInfo(String username, MemberUpdateDTO dto) {
		UserEntity userEntity = userRepository.findByUsername(username)
	            .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

	    // 1. 비밀번호 변경 로직
	    if (dto.isPasswordChangeRequest()) {
	        // 현재 비밀번호 일치 확인 (필수)
	        if (!passwordEncoder.matches(dto.getCurrentPassword(), userEntity.getPassword())) {
	            throw new RuntimeException("현재 비밀번호가 일치하지 않습니다.");
	        }
	        
	        // 새 비밀번호 암호화 후 업데이트
	        String encodedNewPassword = passwordEncoder.encode(dto.getNewPassword());
	        userEntity.setPassword(encodedNewPassword);
	    }
	    
	    // 2. 전화번호 및 이메일 업데이트
	    userEntity.setPhoneNumber(dto.getPhoneNumber());
	    userEntity.setEmail(dto.getEmail());
		
	}

	public UserServiceImpl() {

	}
}
