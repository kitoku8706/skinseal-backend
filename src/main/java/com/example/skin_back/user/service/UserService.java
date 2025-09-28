package com.example.skin_back.user.service;

import com.example.skin_back.user.dto.UserDTO;
import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO getUserById(Long userId);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(Long userId, UserDTO userDTO);
    void deleteUser(Long userId);
    boolean existsByEmail(String email);
    boolean login(String email, String password);
}