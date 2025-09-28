package com.example.skin_back.user.service;

import com.example.skin_back.user.dto.UserDTO;
import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO getUserByEmail(String email);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(String email, UserDTO userDTO);
    void deleteUser(String email);
    boolean existsByEmail(String email);
    boolean login(String email, String password);
}