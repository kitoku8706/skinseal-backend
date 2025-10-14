package com.example.skin_back.notice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeDTO {
    private Long noticeId;
    private String title;
    private String content;
    private String username;
    private LocalDateTime createdAt;
    private Long authorId;
    private String type;
    private int views;
}