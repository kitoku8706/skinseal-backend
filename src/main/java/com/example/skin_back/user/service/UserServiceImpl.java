package com.example.skin_back.user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.skin_back.user.config.jwt.JwtTokenProvider;
import com.example.skin_back.user.constant.UserRole;
import com.example.skin_back.user.dto.AuthInfo;
import com.example.skin_back.user.dto.AuthResponseDTO;
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

	public UserServiceImpl() {

	}
}
