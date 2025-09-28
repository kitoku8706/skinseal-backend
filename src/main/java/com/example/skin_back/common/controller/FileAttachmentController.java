package com.example.skin_back.common.controller;

import com.example.skin_back.common.dto.FileAttachmentDTO;
import com.example.skin_back.common.service.FileAttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileAttachmentController {
    private final FileAttachmentService fileAttachmentService;

    @PostMapping("/upload/{noticeId}")
    public ResponseEntity<FileAttachmentDTO> uploadFile(@RequestParam("file") MultipartFile file,
                                                        @PathVariable Long noticeId) {
        FileAttachmentDTO dto = fileAttachmentService.uploadFile(file, noticeId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<FileAttachmentDTO> getFile(@PathVariable Long fileId) {
        FileAttachmentDTO dto = fileAttachmentService.getFile(fileId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/notice/{noticeId}")
    public ResponseEntity<List<FileAttachmentDTO>> getFilesByNotice(@PathVariable Long noticeId) {
        List<FileAttachmentDTO> files = fileAttachmentService.getFilesByNotice(noticeId);
        return ResponseEntity.ok(files);
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long fileId) {
        FileAttachmentDTO dto = fileAttachmentService.getFile(fileId);
        byte[] data = fileAttachmentService.downloadFile(fileId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + dto.getOriginalName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(data);
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long fileId) {
        fileAttachmentService.deleteFile(fileId);
        return ResponseEntity.noContent().build();
    }
}