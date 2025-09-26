package com.example.skin_back.disease.controller;

import com.example.skin_back.disease.dto.DiseaseDTO;
import com.example.skin_back.disease.service.DiseaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disease")
@RequiredArgsConstructor
public class DiseaseController {
    private final DiseaseService diseaseService;

    @PostMapping
    public ResponseEntity<DiseaseDTO> create(@RequestBody DiseaseDTO dto) {
        return ResponseEntity.ok(diseaseService.createDisease(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiseaseDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(diseaseService.getDiseaseById(id));
    }

    @GetMapping
    public ResponseEntity<List<DiseaseDTO>> getAll() {
        return ResponseEntity.ok(diseaseService.getAllDiseases());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiseaseDTO> update(@PathVariable Long id, @RequestBody DiseaseDTO dto) {
        return ResponseEntity.ok(diseaseService.updateDisease(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        diseaseService.deleteDisease(id);
        return ResponseEntity.noContent().build();
    }
}