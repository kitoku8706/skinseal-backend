package com.example.skin_back.user.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.skin_back.user.dto.UserDTO;
import com.example.skin_back.user.entity.UserEntity;
import com.example.skin_back.user.repository.UserRepository;

@RestController
@RequestMapping("/api/user")
public class UserApiController {
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        List<UserDTO> userDTOs = users.stream()
            .map(UserDTO::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }
    
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserEntity entity = userDTO.toEntity();
        // loginId가 없으면 email을 loginId로 사용
        if (entity.getLoginId() == null || entity.getLoginId().isEmpty()) {
            entity.setLoginId(userDTO.getEmail());
        }
        // username이 없으면 email의 @ 앞부분을 username으로 사용
        if (entity.getUsername() == null || entity.getUsername().isEmpty()) {
            entity.setUsername(userDTO.getEmail().split("@")[0]);
        }
        UserEntity saved = userRepository.save(entity);
        return ResponseEntity.ok(UserDTO.toDTO(saved));
    }
    
    @GetMapping("/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        return userRepository.findByEmail(email)
            .map(UserDTO::toDTO)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.ok().build());
    }
    
    @PutMapping("/{email}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String email, @RequestBody UserDTO userDTO) {
        return userRepository.findByEmail(email)
            .map(entity -> {
                if (userDTO.getUsername() != null) entity.setUsername(userDTO.getUsername());
                if (userDTO.getPhoneNumber() != null) entity.setPhoneNumber(userDTO.getPhoneNumber());
                if (userDTO.getPassword() != null) entity.setPassword(userDTO.getPassword());
                UserEntity updated = userRepository.save(entity);
                return ResponseEntity.ok(UserDTO.toDTO(updated));
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable String email) {
        return userRepository.findByEmail(email)
            .map(entity -> {
                userRepository.delete(entity);
                return ResponseEntity.noContent().<Void>build();
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody UserDTO userDTO) {
        return userRepository.findByEmail(userDTO.getEmail())
            .map(entity -> {
                boolean matches = entity.getPassword().equals(userDTO.getPassword());
                return ResponseEntity.ok(matches);
            })
            .orElse(ResponseEntity.ok(false));
    }
    
    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
        boolean exists = userRepository.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }
}