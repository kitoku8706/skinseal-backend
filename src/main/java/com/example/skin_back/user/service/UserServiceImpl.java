package com.example.skin_back.user.service;

import com.example.skin_back.user.dto.UserDTO;
import com.example.skin_back.user.entity.UserEntity;
import com.example.skin_back.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        UserEntity entity = UserEntity.builder()
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .role(userDTO.getRole())
                .phoneNumber(userDTO.getPhoneNumber())
                .build();
        UserEntity saved = userRepository.save(entity);
        return toDTO(saved);
    }

    @Override
    public UserDTO getUserById(Long userId) {
        return userRepository.findById(userId)
                .map(this::toDTO)
                .orElse(null);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        return userRepository.findById(userId)
                .map(entity -> {
                    entity.setEmail(userDTO.getEmail());
                    entity.setPassword(userDTO.getPassword());
                    entity.setRole(userDTO.getRole());
                    entity.setPhoneNumber(userDTO.getPhoneNumber());
                    return toDTO(userRepository.save(entity));
                })
                .orElse(null);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email) != null;
    }

    @Override
    public boolean login(String email, String password) {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) return false;
        return user.getPassword().equals(password);
    }

    private UserDTO toDTO(UserEntity entity) {
        return UserDTO.builder()
                .userId(entity.getUserId())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .role(entity.getRole())
                .phoneNumber(entity.getPhoneNumber())
                .build();
    }
}