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

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
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
    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<?> saveDiagnosisFromAi(
            @RequestPart("payload") Map<String, Object> payload,
            @RequestPart(value = "image", required = false) MultipartFile imageFile
    ) {
        try {
            Map<String, Object> saved = diagnosisService.saveAiResult(payload, imageFile);
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

    @GetMapping("/history")
    public ResponseEntity<?> getHistory(@RequestParam(name = "userId", required = false) Long userId,
                                        @RequestParam(name = "username", required = false) String username) {
        try {
            Long resolvedUserId = userId;

            // If userId not provided but username is, try to resolve it
            if (resolvedUserId == null && username != null && !username.isBlank()) {
                // 1) if username is numeric, accept it
                try {
                    resolvedUserId = Long.valueOf(username);
                } catch (Exception parseEx) {
                    // 2) attempt to call member service to resolve username -> id
                    try {
                        RestTemplate rt = new RestTemplate();
                        String memberUrl = "http://localhost:8090/api/member/user?username=" + username;
                        ResponseEntity<Map> resp = rt.getForEntity(memberUrl, Map.class);
                        if (resp.getStatusCode().is2xxSuccessful() && resp.getBody() != null) {
                            Map body = resp.getBody();
                            Object idObj = body.get("id");
                            if (idObj == null) idObj = body.get("userId");
                            if (idObj != null) {
                                resolvedUserId = Long.valueOf(idObj.toString());
                            }
                        }
                    } catch (Exception ex) {
                        return ResponseEntity.status(400).body(Map.of(
                                "error", "username resolution failed",
                                "message", ex.getMessage(),
                                "note", "No local member endpoint found or it returned unexpected shape. Provide numeric userId if possible."
                        ));
                    }
                }
            }

            if (resolvedUserId == null) {
                return ResponseEntity.status(400).body(Map.of("error", "userId or username query parameter is required"));
            }

            var list = diagnosisService.getHistoryForUser(resolvedUserId);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "history fetch failed", "message", e.getMessage()));
        }
    }

    @GetMapping("/latest")
    public ResponseEntity<?> getLatest(@RequestParam(name = "userId", required = false) Long userId,
                                       @RequestParam(name = "username", required = false) String username,
                                       @RequestParam(name = "modelName", required = false) String modelName) {
        try {
            Long resolvedUserId = userId;

            if (resolvedUserId == null && username != null && !username.isBlank()) {
                try {
                    resolvedUserId = Long.valueOf(username);
                } catch (Exception parseEx) {
                    try {
                        RestTemplate rt = new RestTemplate();
                        String memberUrl = "http://localhost:8090/api/member/user?username=" + username;
                        ResponseEntity<Map> resp = rt.getForEntity(memberUrl, Map.class);
                        if (resp.getStatusCode().is2xxSuccessful() && resp.getBody() != null) {
                            Map body = resp.getBody();
                            Object idObj = body.get("id");
                            if (idObj == null) idObj = body.get("userId");
                            if (idObj != null) {
                                resolvedUserId = Long.valueOf(idObj.toString());
                            }
                        }
                    } catch (Exception ex) {
                        return ResponseEntity.status(400).body(Map.of(
                                "error", "username resolution failed",
                                "message", ex.getMessage(),
                                "note", "Provide numeric userId if possible."
                        ));
                    }
                }
            }

            if (resolvedUserId == null) {
                return ResponseEntity.status(400).body(Map.of("error", "userId or username query parameter is required"));
            }
            if (modelName == null || modelName.isBlank()) {
                return ResponseEntity.status(400).body(Map.of("error", "modelName query parameter is required"));
            }

            var opt = diagnosisService.getLatestForUserAndModel(resolvedUserId, modelName);
            if (opt.isPresent()) return ResponseEntity.ok(opt.get());
            return ResponseEntity.status(404).body(Map.of("error", "not_found", "message", "no record for given user/model"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "latest fetch failed", "message", e.getMessage()));
        }
    }
}
