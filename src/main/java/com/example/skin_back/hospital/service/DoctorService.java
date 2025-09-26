package com.example.skin_back.hospital.service;

import com.example.skin_back.hospital.dto.DoctorDTO;
import java.util.List;

public interface DoctorService {
    DoctorDTO createDoctor(DoctorDTO doctorDTO);
    DoctorDTO getDoctorById(Long doctorId);
    List<DoctorDTO> getAllDoctors();
    DoctorDTO updateDoctor(Long doctorId, DoctorDTO doctorDTO);
    void deleteDoctor(Long doctorId);
}
