package com.example.skin_back.notice.controller;

import com.example.skin_back.notice.dto.NoticeDTO;
import com.example.skin_back.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @PostMapping
    public ResponseEntity<NoticeDTO> create(@RequestBody NoticeDTO dto) {
        return ResponseEntity.ok(noticeService.createNotice(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoticeDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(noticeService.getNoticeById(id));
    }

    @GetMapping
    public ResponseEntity<List<NoticeDTO>> getAll() {
        return ResponseEntity.ok(noticeService.getAllNotices());
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoticeDTO> update(@PathVariable Long id, @RequestBody NoticeDTO dto) {
        return ResponseEntity.ok(noticeService.updateNotice(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        noticeService.deleteNotice(id);
        return ResponseEntity.noContent().build();
    }
}
