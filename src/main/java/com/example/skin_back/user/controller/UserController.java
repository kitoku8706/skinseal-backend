package com.example.skin_back.user.controller;

import com.example.skin_back.user.dto.ErrorResponse;
import com.example.skin_back.user.dto.LoginResponse;
import com.example.skin_back.user.dto.UserDTO;
import com.example.skin_back.user.dto.UserLoginRequest;
import com.example.skin_back.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserDTO dto) {
        try {
            UserDTO created = userService.createUser(dto);
            return ResponseEntity.status(201).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("서버 오류"));
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDTO> get(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/{email}")
    public ResponseEntity<UserDTO> update(@PathVariable String email, @RequestBody UserDTO dto) {
        return ResponseEntity.ok(userService.updateUser(email, dto));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> delete(@PathVariable String email) {
        userService.deleteUser(email);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/check-email")
    public ResponseEntity<?> checkEmail(@RequestParam("email") String email) {
        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }
        try {
            boolean exists = userService.existsByEmail(email);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("이메일 중복확인 중 서버 오류: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest request) {
        LoginResponse response = userService.loginWithInfo(request.getEmail(), request.getPassword());
        if (!response.success) {
            return ResponseEntity.status(401).body(response);
        }
        return ResponseEntity.ok(response);
    }
}