package com.example.skin_back.notice.repository;

import com.example.skin_back.notice.entity.NoticeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeRepositoryCustom {
    Page<NoticeEntity> search(String keyword, String type, Pageable pageable);
}