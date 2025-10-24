package com.example.skin_back.diagnosis.service;

import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

public interface DiagnosisService {
    Map<String, Object> diagnose(MultipartFile imageFile) throws Exception;
    // AI 서버가 보내는 { userId, modelName, result }를 저장
    Map<String, Object> saveAiResult(Map<String, Object> payload) throws Exception;
}
