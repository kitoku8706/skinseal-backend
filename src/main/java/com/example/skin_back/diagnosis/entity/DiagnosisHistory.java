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

    @Lob
    private String result;

    private LocalDateTime createdAt;

    // 생성자, getter, setter
    public DiagnosisHistory() {}

    public DiagnosisHistory(String imagePath, String result, LocalDateTime createdAt) {
        this.imagePath = imagePath;
        this.result = result;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}