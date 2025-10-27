package com.example.skin_back.diagnosis.repository;

import com.example.skin_back.diagnosis.entity.DiagnosisHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiagnosisHistoryRepository extends JpaRepository<DiagnosisHistory, Long> {
    Optional<DiagnosisHistory> findTopByUserIdAndModelNameOrderByCreatedAtDesc(Long userId, String modelName);
}
