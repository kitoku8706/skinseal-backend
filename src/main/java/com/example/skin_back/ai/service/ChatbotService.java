package com.example.skin_back.ai.service;

import com.example.skin_back.ai.entity.ChatbotCategory;
import com.example.skin_back.ai.repository.ChatbotCategoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ChatbotService {
    private final ChatbotCategoryRepository repository;

    public ChatbotService(ChatbotCategoryRepository repository) {
        this.repository = repository;
    }

    public List<ChatbotCategory> getEnabledCategories() {
        return repository.findByEnabledTrue();
    }

    public Optional<ChatbotCategory> getCategoryAnswer(Long id) {
        return repository.findById(id);
    }
}
