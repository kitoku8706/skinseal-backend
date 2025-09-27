package com.example.skin_back.common.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileAttachmentDTO {
    private Long fileId;
    private String originalName;
    private String storedName;
    private String filePath;
    private Long fileSize;
    private Long noticeId;
}
