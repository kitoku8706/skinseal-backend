package com.example.skin_back.notice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeDTO {
    private Long noticeId;
    
    @NotBlank(message = "제목은 필수입니다")
    private String title;
    
    @NotBlank(message = "내용은 필수입니다")
    private String content;
    
    private String username;
    private LocalDateTime createdAt;
    private Long authorId;
    private String type;
    private int views;
}