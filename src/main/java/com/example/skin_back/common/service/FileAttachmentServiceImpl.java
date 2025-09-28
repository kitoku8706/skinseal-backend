package com.example.skin_back.common.service;

import com.example.skin_back.common.dto.FileAttachmentDTO;
import com.example.skin_back.common.entity.FileAttachmentEntity;
import com.example.skin_back.common.repository.FileAttachmentRepository;
import com.example.skin_back.notice.entity.NoticeEntity;
import com.example.skin_back.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileAttachmentServiceImpl implements FileAttachmentService {
    private final FileAttachmentRepository fileAttachmentRepository;
    private final NoticeRepository noticeRepository;
    private final String uploadDir = "uploads";

    @Transactional
    @Override
    public FileAttachmentDTO uploadFile(MultipartFile file, Long noticeId) {
        try {
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();
            String storedName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            String filePath = uploadDir + File.separator + storedName;
            file.transferTo(new File(filePath));
            NoticeEntity notice = noticeRepository.findById(noticeId).orElseThrow();
            FileAttachmentEntity entity = FileAttachmentEntity.builder()
                    .originalName(file.getOriginalFilename())
                    .storedName(storedName)
                    .filePath(filePath)
                    .fileSize(file.getSize())
                    .notice(notice)
                    .build();
            entity = fileAttachmentRepository.save(entity);
            return toDTO(entity);
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 실패", e);
        }
    }

    @Override
    public FileAttachmentDTO getFile(Long fileId) {
        FileAttachmentEntity entity = fileAttachmentRepository.findById(fileId).orElseThrow();
        return toDTO(entity);
    }

    @Override
    public List<FileAttachmentDTO> getFilesByNotice(Long noticeId) {
        return fileAttachmentRepository.findByNotice_NoticeId(noticeId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public byte[] downloadFile(Long fileId) {
        FileAttachmentEntity entity = fileAttachmentRepository.findById(fileId).orElseThrow();
        try {
            return Files.readAllBytes(new File(entity.getFilePath()).toPath());
        } catch (IOException e) {
            throw new RuntimeException("파일 다운로드 실패", e);
        }
    }

    @Transactional
    @Override
    public void deleteFile(Long fileId) {
        FileAttachmentEntity entity = fileAttachmentRepository.findById(fileId).orElseThrow();
        File file = new File(entity.getFilePath());
        if (file.exists()) file.delete();
        fileAttachmentRepository.delete(entity);
    }

    private FileAttachmentDTO toDTO(FileAttachmentEntity entity) {
        return FileAttachmentDTO.builder()
                .fileId(entity.getFileId())
                .originalName(entity.getOriginalName())
                .storedName(entity.getStoredName())
                .filePath(entity.getFilePath())
                .fileSize(entity.getFileSize())
                .noticeId(entity.getNotice() != null ? entity.getNotice().getNoticeId() : null)
                .build();
    }
}