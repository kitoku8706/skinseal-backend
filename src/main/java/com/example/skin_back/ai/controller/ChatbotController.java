package com.example.skin_back.ai.controller;

import com.example.skin_back.ai.entity.ChatbotCategory;
import com.example.skin_back.ai.service.ChatbotService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chatbot")
public class ChatbotController {
    private final ChatbotService service;

    public ChatbotController(ChatbotService service) {
        this.service = service;
    }

    @GetMapping("/categories")
    public List<Map<String, Object>> getCategories() {
        return service.getEnabledCategories().stream()
            .map(cat -> {
                Map<String, Object> map = new java.util.HashMap<>();
                map.put("id", cat.getId());
                map.put("name", cat.getName());
                return map;
            })
            .collect(Collectors.toList());
    }

    @GetMapping("/answer/{categoryId}")
    public ResponseEntity<Object> getAnswer(@PathVariable Long categoryId) {
        return service.getCategoryAnswer(categoryId)
            .map(category -> ResponseEntity.ok().body((Object) category))
            .orElseGet(() -> ResponseEntity.ok().body(Map.of("message", "카테고리 없음")));
    }

    @GetMapping("/fallback")
    public Map<String, String> getFallback() {
        return Map.of("message", "죄송합니다. 저는 현재 제시된 5가지 메뉴에 대해서만 답변할 수 있습니다. 기타 문의는 000-0000-0000으로 전화해 주세요.");
    }
}