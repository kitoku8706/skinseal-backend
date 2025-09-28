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
        jdbcTemplate.execute("DELETE FROM ss_user");
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
}