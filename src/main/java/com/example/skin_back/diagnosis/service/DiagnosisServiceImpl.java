package com.example.skin_back.diagnosis.service;

import com.example.skin_back.diagnosis.entity.DiagnosisHistory;
import com.example.skin_back.diagnosis.repository.DiagnosisHistoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.PostConstruct;

@Service
public class DiagnosisServiceImpl implements DiagnosisService {
    private static final Logger log = LoggerFactory.getLogger(DiagnosisServiceImpl.class);
    private final DiagnosisHistoryRepository diagnosisHistoryRepository;

    @Value("${diagnosis.upload-dir:uploads}")
    private String uploadDir;

    @Value("${diagnosis.python-server-url}")
    private String pythonServerUrl;

    public DiagnosisServiceImpl(DiagnosisHistoryRepository diagnosisHistoryRepository) {
        this.diagnosisHistoryRepository = diagnosisHistoryRepository;
    }

    @PostConstruct
    void logConfigOnStart() {
        log.info("DiagnosisService config: uploadDir={}, pythonServerUrl={}", uploadDir, pythonServerUrl);
    }

    @Override
    public Map<String, Object> diagnose(MultipartFile imageFile) throws IOException {
        // 1. 이미지 저장
        String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();
        File dest = new File(dir, fileName);
        imageFile.transferTo(dest);

        try {
            // 2. Python AI 서버로 이미지 전송
            RestTemplate restTemplate = new RestTemplate();
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("image", new org.springframework.core.io.FileSystemResource(dest));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(pythonServerUrl, requestEntity, String.class);
            String aiResultRaw = response.getBody();

            // JSON 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> aiResult = objectMapper.readValue(aiResultRaw, new TypeReference<Map<String, Object>>() {});

            // 3. DB 저장(이미지 업로드 플로우)
            DiagnosisHistory history = new DiagnosisHistory(dest.getAbsolutePath(), aiResultRaw, LocalDateTime.now());
            diagnosisHistoryRepository.save(history);

            // 4. 결과 반환
            Map<String, Object> result = new HashMap<>();
            result.put("aiResult", aiResult);
            result.put("historyId", history.getId());
            return result;
        } catch (Exception e) {
            log.error("Failed to call Python AI server. url={}, error={}", pythonServerUrl, e.toString());
            throw e;
        }
    }

    @Override
    public Map<String, Object> saveAiResult(Map<String, Object> payload) throws IOException {
        // payload: { userId, modelName, result: [ {class, probability}, ... ] }
        ObjectMapper om = new ObjectMapper();
        String rawJson = om.writeValueAsString(payload);

        Long userId = null;
        String modelName = null;
        try {
            Object uid = payload.get("userId");
            if (uid != null) userId = Long.valueOf(uid.toString());
        } catch (Exception ignore) {}
        Object mn = payload.get("modelName");
        if (mn != null) modelName = mn.toString();

        DiagnosisHistory history = new DiagnosisHistory(userId, modelName, rawJson, LocalDateTime.now());
        diagnosisHistoryRepository.save(history);

        Map<String, Object> ret = new HashMap<>();
        ret.put("historyId", history.getId());
        ret.put("userId", userId);
        ret.put("modelName", modelName);
        return ret;
    }

    @Override
    public List<Map<String, Object>> getHistoryForUser(Long userId) throws Exception {
        log.info("Fetching latest diagnosis history for userId={}", userId);
        try {
            // Find records for the user, sort by createdAt desc and take the most recent one
            List<DiagnosisHistory> items = diagnosisHistoryRepository.findAll().stream()
                    .filter(h -> h.getUserId() != null && h.getUserId().equals(userId))
                    .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                    .limit(1)
                    .collect(Collectors.toList());

            ObjectMapper om = new ObjectMapper();
            List<Map<String, Object>> out = items.stream().map(h -> {
                try {
                    String raw = h.getResult();
                    if (raw == null || raw.isBlank()) {
                        Map<String, Object> empty = new HashMap<>();
                        empty.put("historyId", h.getId());
                        empty.put("createdAt", h.getCreatedAt().toString());
                        empty.put("note", "empty result");
                        return empty;
                    }

                    Object parsed = null;
                    try {
                        parsed = om.readValue(raw, Object.class);
                    } catch (Exception pe) {
                        log.warn("Failed to parse history.result JSON for id={}: {}", h.getId(), pe.toString());
                    }

                    Map<String, Object> m = new HashMap<>();
                    if (parsed instanceof Map) {
                        //noinspection unchecked
                        m.putAll((Map<String, Object>) parsed);
                    } else if (parsed instanceof List) {
                        m.put("result", parsed);
                    } else {
                        m.put("raw", raw);
                    }
                    m.put("historyId", h.getId());
                    m.put("createdAt", h.getCreatedAt().toString());
                    return m;
                } catch (Exception e) {
                    log.error("Failed to convert DiagnosisHistory id={} to response map: {}", h.getId(), e.toString());
                    Map<String, Object> fallback = new HashMap<>();
                    fallback.put("historyId", h.getId());
                    fallback.put("raw", h.getResult());
                    fallback.put("createdAt", h.getCreatedAt().toString());
                    fallback.put("error", e.toString());
                    return fallback;
                }
            }).collect(Collectors.toList());
            return out;
        } catch (Exception e) {
            log.error("Unexpected error in getHistoryForUser for userId={}: {}", userId, e.toString());
            return List.of();
        }
    }

    @Override
    public Optional<Map<String, Object>> getLatestForUserAndModel(Long userId, String modelName) {
        Optional<DiagnosisHistory> opt = diagnosisHistoryRepository.findTopByUserIdAndModelNameOrderByCreatedAtDesc(userId, modelName);
        if (!opt.isPresent()) return Optional.empty();
        DiagnosisHistory dh = opt.get();
        Map<String, Object> out = new HashMap<>();
        out.put("id", dh.getId());
        out.put("userId", dh.getUserId());
        out.put("modelName", dh.getModelName());
        out.put("imagePath", dh.getImagePath());
        out.put("createdAt", dh.getCreatedAt() != null ? dh.getCreatedAt().toString() : null);
        // Try to parse result JSON into structure; if fails, include raw string
        String raw = dh.getResult();
        if (raw != null && !raw.isEmpty()) {
            try {
                ObjectMapper om = new ObjectMapper();
                Object parsed = om.readValue(raw, Object.class);
                out.put("result", parsed);
            } catch (Exception e) {
                out.put("result", raw);
            }
        } else {
            out.put("result", null);
        }
        return Optional.of(out);
    }
}