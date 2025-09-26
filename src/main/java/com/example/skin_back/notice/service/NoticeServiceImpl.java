package com.example.skin_back.notice.service;

import com.example.skin_back.notice.dto.NoticeDTO;
import com.example.skin_back.notice.entity.NoticeEntity;
import com.example.skin_back.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;

    @Override
    public NoticeDTO createNotice(NoticeDTO noticeDTO) {
        NoticeEntity entity = NoticeEntity.builder()
                .title(noticeDTO.getTitle())
                .content(noticeDTO.getContent())
                .createdAt(noticeDTO.getCreatedAt())
                .authorId(noticeDTO.getAuthorId())
                .build();
        NoticeEntity saved = noticeRepository.save(entity);
        return toDTO(saved);
    }

    @Override
    public NoticeDTO getNoticeById(Long noticeId) {
        return noticeRepository.findById(noticeId)
                .map(this::toDTO)
                .orElse(null);
    }

    @Override
    public List<NoticeDTO> getAllNotices() {
        return noticeRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public NoticeDTO updateNotice(Long noticeId, NoticeDTO noticeDTO) {
        return noticeRepository.findById(noticeId)
                .map(entity -> {
                    entity.setTitle(noticeDTO.getTitle());
                    entity.setContent(noticeDTO.getContent());
                    entity.setCreatedAt(noticeDTO.getCreatedAt());
                    entity.setAuthorId(noticeDTO.getAuthorId());
                    return toDTO(noticeRepository.save(entity));
                })
                .orElse(null);
    }

    @Override
    public void deleteNotice(Long noticeId) {
        noticeRepository.deleteById(noticeId);
    }

    private NoticeDTO toDTO(NoticeEntity entity) {
        return NoticeDTO.builder()
                .noticeId(entity.getNoticeId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .createdAt(entity.getCreatedAt())
                .authorId(entity.getAuthorId())
                .build();
    }
}
