package com.example.skin_back.notice.service;

import com.example.skin_back.notice.dto.NoticeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NoticeService {
    NoticeDTO createNotice(NoticeDTO noticeDTO);
    NoticeDTO getNoticeById(Long noticeId);
    List<NoticeDTO> getAllNotices();
    NoticeDTO updateNotice(Long noticeId, NoticeDTO noticeDTO);
    void deleteNotice(Long noticeId);
    boolean existsById(Long noticeId);

    // New: search with pagination and optional filters
    Page<NoticeDTO> searchNotices(String keyword, String type, Pageable pageable);
}