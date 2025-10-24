package com.example.skin_back.diagnosis.controller;

import com.example.skin_back.diagnosis.service.DiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/diagnosis")
public class DiagnosisController {
    private final DiagnosisService diagnosisService;

    @Value("${diagnosis.upload-dir:uploads}")
    private String uploadDir;

    @Value("${diagnosis.python-server-url}")
    private String pythonServerUrl;

    @Autowired
    public DiagnosisController(DiagnosisService diagnosisService) {
        this.diagnosisService = diagnosisService;
    }

    @PostMapping("/efficientnet")
    public ResponseEntity<?> diagnose(@RequestParam("image") MultipartFile imageFile) {
        try {
            Map<String, Object> result = diagnosisService.diagnose(imageFile);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                "error", "진단 요청 실패",
                "message", e.getMessage()
            ));
        }
    }

    // Python AI 서버가 직접 저장을 위해 호출하는 엔드포인트
    @PostMapping
    public ResponseEntity<?> saveDiagnosisFromAi(@RequestBody Map<String, Object> payload) {
        try {
            Map<String, Object> saved = diagnosisService.saveAiResult(payload);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of(
                "error", "유효하지 않은 요청",
                "message", e.getMessage()
            ));
        }
    }

    @GetMapping("/config")
    public Map<String, Object> config() {
        Map<String, Object> m = new HashMap<>();
        m.put("uploadDir", uploadDir);
        m.put("pythonServerUrl", pythonServerUrl);
        return m;
    }

    @GetMapping("/ping")
    public ResponseEntity<?> pingPython() {
        try {
            String baseUrl = pythonServerUrl.replaceAll("/efficientnet/?$", "/");
            RestTemplate rt = new RestTemplate();
            ResponseEntity<String> res = rt.exchange(baseUrl, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), String.class);
            Map<String, Object> m = new HashMap<>();
            m.put("url", baseUrl);
            m.put("status", res.getStatusCode().value());
            m.put("body", res.getBody());
            return ResponseEntity.ok(m);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.toString(), "url", pythonServerUrl));
        }
    }
}
