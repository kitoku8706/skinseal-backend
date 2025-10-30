package com.example.skin_back.diagnosis.service;

import com.example.skin_back.diagnosis.entity.DiagnosisHistory;
import com.example.skin_back.diagnosis.repository.DiagnosisHistoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiagnosisServiceImpl implements DiagnosisService {
    private static final Logger log = LoggerFactory.getLogger(DiagnosisServiceImpl.class);
    private final DiagnosisHistoryRepository diagnosisHistoryRepository;

    @Value("${diagnosis.python-server-url}")
    private String pythonServerUrl;

    public DiagnosisServiceImpl(DiagnosisHistoryRepository diagnosisHistoryRepository) {
        this.diagnosisHistoryRepository = diagnosisHistoryRepository;
    }

    @PostConstruct
    void logConfigOnStart() {
        log.info("DiagnosisService config: pythonServerUrl={}", pythonServerUrl);
    }

    @Override
    public Map<String, Object> diagnose(MultipartFile imageFile) throws IOException {
        // This method is deprecated.
        throw new UnsupportedOperationException("This method is deprecated.");
    }

    @Override
    public Map<String, Object> saveAiResult(Map<String, Object> payload, MultipartFile imageFile) throws IOException {
        ObjectMapper om = new ObjectMapper();
        String rawJsonResult = om.writeValueAsString(payload.get("result"));

        Long userId = null;
        String modelName = null;
        try {
            Object uid = payload.get("userId");
            if (uid != null) userId = Long.valueOf(uid.toString());
        } catch (Exception ignore) {
            log.warn("Could not parse userId from payload");
        }
        Object mn = payload.get("modelName");
        if (mn != null) modelName = mn.toString();

        byte[] imageData = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            imageData = imageFile.getBytes();
        }

        DiagnosisHistory history = new DiagnosisHistory(userId, modelName, rawJsonResult, LocalDateTime.now(), imageData);
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
        out.put("imageData", dh.getImageData());
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