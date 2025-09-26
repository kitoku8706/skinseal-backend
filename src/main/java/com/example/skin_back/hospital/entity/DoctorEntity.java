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
@Table(name = "DOCTOR")
public class DoctorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctor_seq_generator")
    @SequenceGenerator(name = "doctor_seq_generator", sequenceName = "DOCTOR_ID_SEQ", allocationSize = 1)
    @Column(name = "DOCTOR_ID")
    private Long doctorId;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "SPECIALTY")
    private String specialty;

    @Column(name = "BIO", columnDefinition = "CLOB")
    private String bio;

    @Column(name = "PHOTO_URL")
    private String photoUrl;
}