package com.example.skin_back.user.service;

import com.example.skin_back.user.dto.LoginResponse;
import com.example.skin_back.user.dto.UserDTO;
import com.example.skin_back.user.entity.UserEntity;
import com.example.skin_back.user.repository.UserRepository;
import com.example.skin_back.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        if (existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("이미 등록된 이메일입니다.");
        }
        String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
        UserEntity entity = UserEntity.builder()
                .email(userDTO.getEmail())
                .password(hashedPassword)
                .role(userDTO.getRole())
                .phoneNumber(userDTO.getPhoneNumber())
                .build();
        UserEntity saved = userRepository.save(entity);
        return toDTO(saved);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        UserEntity entity = userRepository.findByEmail(email);
        return entity != null ? toDTO(entity) : null;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(String email, UserDTO userDTO) {
        UserEntity entity = userRepository.findByEmail(email);
        if (entity == null) return null;
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            entity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        entity.setRole(userDTO.getRole());
        entity.setPhoneNumber(userDTO.getPhoneNumber());
        return toDTO(userRepository.save(entity));
    }

    @Override
    public void deleteUser(String email) {
        UserEntity entity = userRepository.findByEmail(email);
        if (entity != null) userRepository.delete(entity);
    }

    @Override
    public boolean existsByEmail(String email) {
        try {
            return userRepository.findByEmail(email) != null;
        } catch (Exception e) {
            // DB 오류 등 발생 시 false 반환
            return false;
        }
    }

    @Override
    public LoginResponse loginWithInfo(String email, String password) {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            return new LoginResponse(false, "이메일 또는 비밀번호가 올바르지 않습니다.", null, null, null);
        }
        boolean match = passwordEncoder.matches(password, user.getPassword());
        if (!match) {
            return new LoginResponse(false, "이메일 또는 비밀번호가 올바르지 않습니다.", null, null, null);
        }
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        return new LoginResponse(true, "로그인 성공", user.getEmail(), user.getRole(), token);
    }

    // Keep old login for compatibility
    @Override
    public boolean login(String email, String password) {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) return false;
        return passwordEncoder.matches(password, user.getPassword());
    }

    private UserDTO toDTO(UserEntity entity) {
        return UserDTO.builder()
                .email(entity.getEmail())
                .password(entity.getPassword())
                .role(entity.getRole())
                .phoneNumber(entity.getPhoneNumber())
                .build();
    }
}