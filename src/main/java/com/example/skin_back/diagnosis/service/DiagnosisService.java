package com.example.skin_back.diagnosis.service;

import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

public interface DiagnosisService {
    Map<String, Object> diagnose(MultipartFile imageFile) throws Exception;
}
