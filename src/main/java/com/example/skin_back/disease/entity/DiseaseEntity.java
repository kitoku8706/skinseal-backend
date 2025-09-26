package com.example.skin_back.disease.entity;

import jakarta.persistence.*;
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
@Table(name = "DISEASE")
public class DiseaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "disease_seq_generator")
    @SequenceGenerator(name = "disease_seq_generator", sequenceName = "DISEASE_ID_SEQ", allocationSize = 1)
    @Column(name = "DISEASE_ID")
    private Long diseaseId;

    @Column(name = "DISEASE_NAME", nullable = false)
    private String diseaseName;

    @Column(name = "DESCRIPTION", columnDefinition = "CLOB")
    private String description;

    @Column(name = "SYMPTOMS")
    private String symptoms;

    @Column(name = "CAUSES")
    private String causes;
}