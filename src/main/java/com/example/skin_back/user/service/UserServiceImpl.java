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
        entity.setPassword(userDTO.getPassword());
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
    public boolean login(String email, String password) {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) return false;
        return user.getPassword().equals(password);
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