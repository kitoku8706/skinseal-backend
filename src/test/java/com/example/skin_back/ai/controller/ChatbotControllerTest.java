package com.example.skin_back.ai.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ChatbotControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void testGetCategories() throws Exception {
        mockMvc.perform(get("/api/chatbot/categories"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetAnswer() throws Exception {
        // 카테고리 id=1이 있다고 가정하고 테스트
        mockMvc.perform(get("/api/chatbot/answer/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testFallback() throws Exception {
        mockMvc.perform(get("/api/chatbot/fallback"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").exists());
    }
}
