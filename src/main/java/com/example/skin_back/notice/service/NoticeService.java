package com.example.skin_back.notice.service;

import com.example.skin_back.notice.dto.NoticeDTO;
import java.util.List;

public interface NoticeService {
    NoticeDTO createNotice(NoticeDTO noticeDTO);
    NoticeDTO getNoticeById(Long noticeId);
    List<NoticeDTO> getAllNotices();
    NoticeDTO updateNotice(Long noticeId, NoticeDTO noticeDTO);
    void deleteNotice(Long noticeId);
}
