package com.example.skin_back.diagnosis.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "diagnosis_history")
public class DiagnosisHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "image_data", columnDefinition="LONGBLOB")
    private byte[] imageData;

    // 새 필드: 사용자 ID
    private Long userId;

    // 새 필드: 사용된 모델명
    private String modelName;    
    @Column(columnDefinition = "TEXT")
    private String result;

    private LocalDateTime createdAt;

    public DiagnosisHistory() {}

    // JSON 및 이미지 데이터 저장 흐름용 생성자
    public DiagnosisHistory(Long userId, String modelName, String result, LocalDateTime createdAt, byte[] imageData) {
        this.userId = userId;
        this.modelName = modelName;
        this.result = result;
        this.createdAt = createdAt;
        this.imageData = imageData;
    }

    public Long getId() { return id; }

    public byte[] getImageData() { return imageData; }
    public void setImageData(byte[] imageData) { this.imageData = imageData; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getModelName() { return modelName; }
    public void setModelName(String modelName) { this.modelName = modelName; }

    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}