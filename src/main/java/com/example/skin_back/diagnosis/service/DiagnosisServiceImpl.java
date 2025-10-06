package com.example.skin_back.diagnosis.service;

import com.example.skin_back.diagnosis.entity.DiagnosisHistory;
import com.example.skin_back.diagnosis.repository.DiagnosisHistoryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class DiagnosisServiceImpl implements DiagnosisService {
    private final DiagnosisHistoryRepository diagnosisHistoryRepository;

    @Value("${diagnosis.upload-dir:uploads}")
    private String uploadDir;

    @Value("${diagnosis.python-server-url}")
    private String pythonServerUrl;

    public DiagnosisServiceImpl(DiagnosisHistoryRepository diagnosisHistoryRepository) {
        this.diagnosisHistoryRepository = diagnosisHistoryRepository;
    }

    @Override
    public Map<String, Object> diagnose(MultipartFile imageFile) throws IOException {
        // 1. 이미지 저장
        String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();
        File dest = new File(dir, fileName);
        imageFile.transferTo(dest);

        // 2. Python AI 서버로 이미지 전송
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", new org.springframework.core.io.FileSystemResource(dest));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(pythonServerUrl, requestEntity, String.class);
        String aiResult = response.getBody();

        // 3. DB 저장
        DiagnosisHistory history = new DiagnosisHistory(dest.getAbsolutePath(), aiResult, LocalDateTime.now());
        diagnosisHistoryRepository.save(history);

        // 4. 결과 반환 (JSON 파싱)
        // 실제로는 aiResult를 JSON으로 파싱해서 반환해야 함
        // 예시: {"diagnosis":"정상","confidence":0.95}
        Map<String, Object> result = new HashMap<>();
        result.put("aiResult", aiResult); // 원본 결과
        result.put("historyId", history.getId());
        return result;
    }
}