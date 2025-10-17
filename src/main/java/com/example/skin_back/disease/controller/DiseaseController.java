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

    @GetMapping
    public ResponseEntity<List<DiseaseDTO>> getAll() {
        return ResponseEntity.ok(diseaseService.getAllDiseases());
    }

    @GetMapping("/list")
    public ResponseEntity<List<DiseaseDTO>> getAllList() {
        return ResponseEntity.ok(diseaseService.getAllDiseases());
    }

    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<DiseaseDTO> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(diseaseService.getDiseaseById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiseaseDTO> update(@PathVariable("id") Long id, @RequestBody DiseaseDTO dto) {
        return ResponseEntity.ok(diseaseService.updateDisease(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        diseaseService.deleteDisease(id);
        return ResponseEntity.noContent().build();
    }
}