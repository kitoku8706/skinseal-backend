package com.example.skin_back.hospital.entity;

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
@Table(name = "HOSPITAL_INFO")
public class HospitalInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hospital_info_seq_generator")
    @SequenceGenerator(name = "hospital_info_seq_generator", sequenceName = "HOSPITAL_INFO_ID_SEQ", allocationSize = 1)
    @Column(name = "INFO_ID")
    private Long infoId;

    @Column(name = "GREETING")
    private String greeting;

    @Column(name = "LOCATION")
    private String location;
}