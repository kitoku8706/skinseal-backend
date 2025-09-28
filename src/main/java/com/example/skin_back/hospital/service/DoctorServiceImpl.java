package com.example.skin_back.hospital.service;

import com.example.skin_back.hospital.dto.DoctorDTO;
import com.example.skin_back.hospital.entity.DoctorEntity;
import com.example.skin_back.hospital.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;

    @Override
    public DoctorDTO createDoctor(DoctorDTO doctorDTO) {
        DoctorEntity entity = DoctorEntity.builder()
                .name(doctorDTO.getName())
                .specialty(doctorDTO.getSpecialty())
                .bio(doctorDTO.getBio())
                .photoUrl(doctorDTO.getPhotoUrl())
                .build();
        DoctorEntity saved = doctorRepository.save(entity);
        return toDTO(saved);
    }

    @Override
    public DoctorDTO getDoctorById(Long doctorId) {
        return doctorRepository.findById(doctorId)
                .map(this::toDTO)
                .orElse(null);
    }

    @Override
    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DoctorDTO updateDoctor(Long doctorId, DoctorDTO doctorDTO) {
        return doctorRepository.findById(doctorId)
                .map(entity -> {
                    entity.setName(doctorDTO.getName());
                    entity.setSpecialty(doctorDTO.getSpecialty());
                    entity.setBio(doctorDTO.getBio());
                    entity.setPhotoUrl(doctorDTO.getPhotoUrl());
                    return toDTO(doctorRepository.save(entity));
                })
                .orElse(null);
    }

    @Override
    public void deleteDoctor(Long doctorId) {
        doctorRepository.deleteById(doctorId);
    }

    private DoctorDTO toDTO(DoctorEntity entity) {
        return DoctorDTO.builder()
                .doctorId(entity.getDoctorId())
                .name(entity.getName())
                .specialty(entity.getSpecialty())
                .bio(entity.getBio())
                .photoUrl(entity.getPhotoUrl())
                .build();
    }
}