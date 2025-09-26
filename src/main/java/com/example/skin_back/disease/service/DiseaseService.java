package com.example.skin_back.disease.service;

import com.example.skin_back.disease.dto.DiseaseDTO;
import java.util.List;

public interface DiseaseService {
    DiseaseDTO createDisease(DiseaseDTO diseaseDTO);
    DiseaseDTO getDiseaseById(Long diseaseId);
    List<DiseaseDTO> getAllDiseases();
    DiseaseDTO updateDisease(Long diseaseId, DiseaseDTO diseaseDTO);
    void deleteDisease(Long diseaseId);
}