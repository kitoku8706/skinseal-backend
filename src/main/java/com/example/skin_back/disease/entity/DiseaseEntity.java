package com.example.skin_back.disease.entity;

import jakarta.persistence.*;
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
@Entity
@Table(name = "disease")
public class DiseaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "disease_id")
    private Long diseaseId;

    @Column(name = "disease_name", nullable = false)
    private String diseaseName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "symptoms")
    private String symptoms;

    @Column(name = "causes")
    private String causes;
    
    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;
}