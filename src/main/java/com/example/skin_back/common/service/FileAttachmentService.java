package com.example.skin_back.common.service;

import com.example.skin_back.common.dto.FileAttachmentDTO;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface FileAttachmentService {
    FileAttachmentDTO uploadFile(MultipartFile file, Long noticeId);
    FileAttachmentDTO getFile(Long fileId);
    List<FileAttachmentDTO> getFilesByNotice(Long noticeId);
    byte[] downloadFile(Long fileId);
    void deleteFile(Long fileId);
}
