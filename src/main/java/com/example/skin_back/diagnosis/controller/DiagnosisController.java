package com.example.skin_back.diagnosis.controller;

import com.example.skin_back.diagnosis.service.DiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/diagnosis")
public class DiagnosisController {
    private final DiagnosisService diagnosisService;

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
            return ResponseEntity.status(500).body(Map.of("error", "진단 요청 실패"));
        }
    }
}
