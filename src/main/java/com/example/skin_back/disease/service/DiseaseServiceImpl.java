package com.example.skin_back.disease.service;

import com.example.skin_back.disease.dto.DiseaseDTO;
import com.example.skin_back.disease.entity.DiseaseEntity;
import com.example.skin_back.disease.repository.DiseaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiseaseServiceImpl implements DiseaseService {
    private final DiseaseRepository diseaseRepository;

    @Override
    public DiseaseDTO createDisease(DiseaseDTO diseaseDTO) {
        DiseaseEntity entity = DiseaseEntity.builder()
                .diseaseName(diseaseDTO.getDiseaseName())
                .description(diseaseDTO.getDescription())
                .symptoms(diseaseDTO.getSymptoms())
                .causes(diseaseDTO.getCauses())
                .build();
        DiseaseEntity saved = diseaseRepository.save(entity);
        return toDTO(saved);
    }

    @Override
    public DiseaseDTO getDiseaseById(Long diseaseId) {
        return diseaseRepository.findById(diseaseId)
                .map(this::toDTO)
                .orElse(null);
    }

    @Override
    public List<DiseaseDTO> getAllDiseases() {
        return diseaseRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DiseaseDTO updateDisease(Long diseaseId, DiseaseDTO diseaseDTO) {
        return diseaseRepository.findById(diseaseId)
                .map(entity -> {
                    entity.setDiseaseName(diseaseDTO.getDiseaseName());
                    entity.setDescription(diseaseDTO.getDescription());
                    entity.setSymptoms(diseaseDTO.getSymptoms());
                    entity.setCauses(diseaseDTO.getCauses());
                    return toDTO(diseaseRepository.save(entity));
                })
                .orElse(null);
    }

    @Override
    public void deleteDisease(Long diseaseId) {
        diseaseRepository.deleteById(diseaseId);
    }

    private DiseaseDTO toDTO(DiseaseEntity entity) {
        return DiseaseDTO.builder()
                .diseaseId(entity.getDiseaseId())
                .diseaseName(entity.getDiseaseName())
                .description(entity.getDescription())
                .symptoms(entity.getSymptoms())
                .causes(entity.getCauses())
                .build();
    }
}