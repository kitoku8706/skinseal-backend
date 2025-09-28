package com.example.skin_back.user.controller;

import com.example.skin_back.user.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired JdbcTemplate jdbcTemplate;

    @BeforeEach
    void clearUserTable() {
        jdbcTemplate.execute("TRUNCATE TABLE ss_user RESTART IDENTITY CASCADE");
    }

    @Test
    void getAllUsers() throws Exception {
        mockMvc.perform(get("/api/user"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateUser() throws Exception {
        String uniqueEmail = "testuser_" + System.currentTimeMillis() + "@example.com";
        UserDTO dto = UserDTO.builder()
            .email(uniqueEmail)
            .password("testpass")
            .role("USER")
            .phoneNumber("01012345678")
            .build();
        mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value(uniqueEmail));
    }

    @Test
    void testUpdateUser() throws Exception {
        // 회원가입
        String email = "updateuser_" + System.currentTimeMillis() + "@example.com";
        UserDTO dto = UserDTO.builder()
            .email(email)
            .password("testpass")
            .role("USER")
            .phoneNumber("01012345678")
            .build();
        String response = mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();
        UserDTO created = objectMapper.readValue(response, UserDTO.class);
        // 회원정보수정
        created.setPhoneNumber("01087654321");
        mockMvc.perform(put("/api/user/" + created.getEmail())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(created)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.phoneNumber").value("01087654321"));
    }

    @Test
    void testDeleteUser() throws Exception {
        // 회원가입
        String email = "deleteuser_" + System.currentTimeMillis() + "@example.com";
        UserDTO dto = UserDTO.builder()
            .email(email)
            .password("testpass")
            .role("USER")
            .phoneNumber("01012345678")
            .build();
        String response = mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();
        UserDTO created = objectMapper.readValue(response, UserDTO.class);
        // 회원탈퇴
        mockMvc.perform(delete("/api/user/" + created.getEmail()))
            .andExpect(status().isNoContent());
        // 탈퇴 후 조회 시 null 또는 404
        mockMvc.perform(get("/api/user/" + created.getEmail()))
            .andExpect(status().isOk())
            .andExpect(content().string(""));
    }

    @Test
    void testLoginUser() throws Exception {
        // 회원가입
        String email = "loginuser_" + System.currentTimeMillis() + "@example.com";
        UserDTO dto = UserDTO.builder()
            .email(email)
            .password("testpass")
            .role("USER")
            .phoneNumber("01012345678")
            .build();
        mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk());
        // 로그인 요청
        String loginRequest = "{\"email\":\"" + email + "\",\"password\":\"testpass\"}";
        mockMvc.perform(post("/api/user/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(loginRequest))
            .andExpect(status().isOk())
            .andExpect(content().string("true"));
    }

    @Test
    void testCheckEmailDuplicate() throws Exception {
        String email = "duplicate_" + System.currentTimeMillis() + "@example.com";
        UserDTO dto = UserDTO.builder()
            .email(email)
            .password("testpass")
            .role("USER")
            .phoneNumber("01012345678")
            .build();
        // 회원가입
        mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk());
        // 가입된 이메일 중복확인: true 반환
        mockMvc.perform(get("/api/user/check-email").param("email", email))
            .andExpect(status().isOk())
            .andExpect(content().string("true"));
        // 미가입 이메일 중복확인: false 반환
        mockMvc.perform(get("/api/user/check-email").param("email", "notexist@example.com"))
            .andExpect(status().isOk())
            .andExpect(content().string("false"));
    }
}