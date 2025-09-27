package com.example.skin_back.disease.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiseaseDTO {
    private Long diseaseId;
    private String diseaseName;
    private String description;
    private String symptoms;
    private String causes;
}