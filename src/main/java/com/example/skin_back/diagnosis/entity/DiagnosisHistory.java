package com.example.skin_back.diagnosis.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "diagnosis_history")
public class DiagnosisHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imagePath;

    // 새 필드: 사용자 ID
    private Long userId;

    // 새 필드: 사용된 모델명
    private String modelName;

    @Lob
    private String result;

    private LocalDateTime createdAt;

    public DiagnosisHistory() {}

    // 기존 이미지 업로드 흐름용 생성자
    public DiagnosisHistory(String imagePath, String result, LocalDateTime createdAt) {
        this.imagePath = imagePath;
        this.result = result;
        this.createdAt = createdAt;
    }

    // JSON 저장 흐름용 생성자
    public DiagnosisHistory(Long userId, String modelName, String result, LocalDateTime createdAt) {
        this.userId = userId;
        this.modelName = modelName;
        this.result = result;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getModelName() { return modelName; }
    public void setModelName(String modelName) { this.modelName = modelName; }

    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}