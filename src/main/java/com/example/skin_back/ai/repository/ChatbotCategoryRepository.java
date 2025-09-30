package com.example.skin_back.ai.repository;

import com.example.skin_back.ai.entity.ChatbotCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChatbotCategoryRepository extends JpaRepository<ChatbotCategory, Long> {
    List<ChatbotCategory> findByEnabledTrue();
}
