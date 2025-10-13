package com.example.skin_back.notice.controller;

import com.example.skin_back.notice.dto.NoticeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class NoticeControllerTest {
    @Autowired MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllNotices() throws Exception {
        mockMvc.perform(get("/api/notice"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("공지사항 생성, 조회, 수정, 삭제 통합 테스트")
    void noticeCrudTest() throws Exception {
        // 1. 공지사항 생성
        NoticeDTO createDto = NoticeDTO.builder()
                .title("테스트 공지사항")
                .content("이것은 테스트용 공지사항입니다.")
                .authorId(1L) // 실제 DB에 존재하는 authorId로 변경 필요
                .build();

        String createJson = objectMapper.writeValueAsString(createDto);

        ResultActions createResult = mockMvc.perform(post("/api/notice")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson));

        createResult.andExpect(status().isOk())
                .andExpect(jsonPath("$.noticeId").exists())
                .andExpect(jsonPath("$.title").value("테스트 공지사항"));

        String response = createResult.andReturn().getResponse().getContentAsString();
        NoticeDTO created = objectMapper.readValue(response, NoticeDTO.class);
        Long noticeId = created.getNoticeId();

        // 2. 공지사항 단건 조회
        mockMvc.perform(get("/api/notice/" + noticeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("테스트 공지사항"));

        // 3. 공지사항 전체 조회
        mockMvc.perform(get("/api/notice"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].noticeId").exists());

        // 4. 공지사항 수정
        NoticeDTO updateDto = NoticeDTO.builder()
                .title("수정된 공지사항")
                .content("수정된 내용입니다.")
                .authorId(1L)
                .build();
        String updateJson = objectMapper.writeValueAsString(updateDto);

        mockMvc.perform(put("/api/notice/" + noticeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("수정된 공지사항"));

        // 5. 공지사항 삭제
        mockMvc.perform(delete("/api/notice/" + noticeId))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("필수값 누락 시 400 Bad Request 테스트")
    void createNoticeMissingRequiredFields() throws Exception {
        // title 누락
        NoticeDTO dtoNoTitle = NoticeDTO.builder()
                .content("내용만 있음")
                .authorId(1L)
                .build();
        mockMvc.perform(post("/api/notice")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoNoTitle)))
                .andExpect(status().isBadRequest());

        // content 누락
        NoticeDTO dtoNoContent = NoticeDTO.builder()
                .title("제목만 있음")
                .authorId(1L)
                .build();
        mockMvc.perform(post("/api/notice")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoNoContent)))
                .andExpect(status().isBadRequest());

        // authorId 누락
        NoticeDTO dtoNoAuthor = NoticeDTO.builder()
                .title("제목")
                .content("내용")
                .build();
        mockMvc.perform(post("/api/notice")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoNoAuthor)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("존재하지 않는 공지사항 접근 시 404 Not Found 테스트")
    void notFoundNoticeTest() throws Exception {
        Long notExistId = 999999L;
        mockMvc.perform(get("/api/notice/" + notExistId))
                .andExpect(status().isNotFound());
        mockMvc.perform(put("/api/notice/" + notExistId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(NoticeDTO.builder()
                        .title("수정")
                        .content("수정")
                        .authorId(1L)
                        .build())))
                .andExpect(status().isNotFound());
        mockMvc.perform(delete("/api/notice/" + notExistId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("공지사항 조회수 증가 테스트")
    void increaseViewCountTest() throws Exception {
        // 1. 공지사항 생성
        NoticeDTO createDto = NoticeDTO.builder()
                .title("조회수 증가 테스트")
                .content("조회수 증가 테스트 내용")
                .authorId(1L) // 실제 DB에 존재하는 authorId로 변경 필요
                .type("GENERAL") // 필수 필드 추가
                .build();

        String createJson = objectMapper.writeValueAsString(createDto);

        ResultActions createResult = mockMvc.perform(post("/api/notice")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson));

        createResult.andExpect(status().isOk())
                .andExpect(jsonPath("$.noticeId").exists());

        String response = createResult.andReturn().getResponse().getContentAsString();
        NoticeDTO created = objectMapper.readValue(response, NoticeDTO.class);

        // 2. 공지사항 조회 (조회수 증가 확인)
        Long noticeId = created.getNoticeId();
        mockMvc.perform(get("/api/notice/" + noticeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.views").value(1));

        // 3. 공지사항 재조회 (조회수 증가 확인)
        mockMvc.perform(get("/api/notice/" + noticeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.views").value(2));
    }
}