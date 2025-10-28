package com.example.skin_back.diagnosis.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "diagnosis_history")
public class DiagnosisHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;

    private String imagePath;

    private String result;

    private String modelName;

    private Long userId;

    public DiagnosisHistory() {}

    public DiagnosisHistory(String imagePath, String result, LocalDateTime createdAt) {
        this.imagePath = imagePath;
        this.result = result;
        this.createdAt = createdAt;
    }

    public DiagnosisHistory(Long userId, String modelName, String result, LocalDateTime createdAt) {
        this.userId = userId;
        this.modelName = modelName;
        this.result = result;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
    public String getModelName() { return modelName; }
    public void setModelName(String modelName) { this.modelName = modelName; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}