package com.example.skin_back.notice.service;

import com.example.skin_back.notice.dto.NoticeDTO;
import com.example.skin_back.notice.entity.NoticeEntity;
import com.example.skin_back.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
                .type(noticeDTO.getType())
                .build();
        NoticeEntity saved = noticeRepository.save(entity);
        return toDTO(saved);
    }

    @Override
    public NoticeDTO getNoticeById(Long noticeId) {
        return noticeRepository.findById(noticeId)
                .map(entity -> {
                    entity.setViews(entity.getViews() + 1); // 조회수 증가
                    noticeRepository.save(entity); // 변경사항 저장
                    return toDTO(entity);
                })
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

    @Override
    public boolean existsById(Long noticeId) {
        return noticeRepository.existsById(noticeId);
    }

    // New: search with pagination
    @Override
    public Page<NoticeDTO> searchNotices(String keyword, String type, Pageable pageable) {
        // Use custom repository search implementation
        Page<NoticeEntity> page = noticeRepository.search((keyword == null || keyword.isBlank()) ? null : keyword, (type == null || type.isBlank()) ? null : type, pageable);
        return page.map(this::toDTO);
    }

    private NoticeDTO toDTO(NoticeEntity entity) {
        return NoticeDTO.builder()
                .noticeId(entity.getNoticeId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .createdAt(entity.getCreatedAt())
                .authorId(entity.getAuthorId())
                .type(entity.getType())
                .views(entity.getViews())
                .build();
    }
}