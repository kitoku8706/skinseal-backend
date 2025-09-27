package com.example.skin_back.common.repository;

import com.example.skin_back.common.entity.FileAttachmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FileAttachmentRepository extends JpaRepository<FileAttachmentEntity, Long> {
    List<FileAttachmentEntity> findByNotice_NoticeId(Long noticeId);
}
