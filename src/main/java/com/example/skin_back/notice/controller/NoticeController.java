package com.example.skin_back.notice.controller;

import com.example.skin_back.notice.dto.NoticeDTO;
import com.example.skin_back.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @PostMapping
    public ResponseEntity<NoticeDTO> create(@Valid @RequestBody NoticeDTO dto) {
        return ResponseEntity.ok(noticeService.createNotice(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoticeDTO> get(@PathVariable("id") Long id) {
        NoticeDTO notice = noticeService.getNoticeById(id);
        if (notice == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(notice);
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "type", required = false) String type) {
        
        // Native Query 사용 시 정렬은 SQL에서 처리됨 (ORDER BY created_at DESC)
        PageRequest pageable = PageRequest.of(page, size);
        Page<NoticeDTO> result = noticeService.searchNotices(keyword, type, pageable);
        
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoticeDTO> update(@PathVariable("id") Long id, @Valid @RequestBody NoticeDTO dto) {
        NoticeDTO updated = noticeService.updateNotice(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!noticeService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        noticeService.deleteNotice(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<NoticeDTO>> search(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "createdAt,desc") String sort
    ) {
        // NOTE: native repository query already defines ORDER BY n.created_at DESC.
        // To avoid Hibernate appending an additional ORDER BY using the entity property
        // name (which causes column name mismatch in native SQL), pass an unsorted PageRequest.
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<NoticeDTO> result = noticeService.searchNotices(keyword, type, pageRequest);
        return ResponseEntity.ok(result);
    }
}