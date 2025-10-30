package com.example.skin_back.diagnosis.service;

import org.springframework.web.multipart.MultipartFile;
import java.util.Map;
import java.util.List;
import java.util.Optional;

public interface DiagnosisService {
    Map<String, Object> diagnose(MultipartFile imageFile) throws Exception;
    // AI 서버가 보내는 { userId, modelName, result }와 이미지를 함께 저장
    Map<String, Object> saveAiResult(Map<String, Object> payload, MultipartFile imageFile) throws Exception;

    // 특정 사용자(userId)에 대한 진단 이력 조회
    List<Map<String, Object>> getHistoryForUser(Long userId) throws Exception;

    Optional<Map<String, Object>> getLatestForUserAndModel(Long userId, String modelName);
}
