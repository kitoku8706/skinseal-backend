package com.example.skin_back.diagnosis.repository;

import com.example.skin_back.diagnosis.entity.DiagnosisHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiagnosisHistoryRepository extends JpaRepository<DiagnosisHistory, Long> {
}
