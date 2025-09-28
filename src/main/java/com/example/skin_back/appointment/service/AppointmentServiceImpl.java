package com.example.skin_back.appointment.service;

import com.example.skin_back.appointment.dto.AppointmentDTO;
import com.example.skin_back.appointment.entity.AppointmentEntity;
import com.example.skin_back.appointment.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;

    @Override
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO) {
        AppointmentEntity entity = AppointmentEntity.builder()
                .userId(appointmentDTO.getUserId())
                .appointmentDate(appointmentDTO.getAppointmentDate())
                .appointmentTime(appointmentDTO.getAppointmentTime())
                .purpose(appointmentDTO.getPurpose())
                .build();
        AppointmentEntity saved = appointmentRepository.save(entity);
        return toDTO(saved);
    }

    @Override
    public AppointmentDTO getAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .map(this::toDTO)
                .orElse(null);
    }

    @Override
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentDTO updateAppointment(Long appointmentId, AppointmentDTO appointmentDTO) {
        return appointmentRepository.findById(appointmentId)
                .map(entity -> {
                    entity.setUserId(appointmentDTO.getUserId());
                    entity.setAppointmentDate(appointmentDTO.getAppointmentDate());
                    entity.setAppointmentTime(appointmentDTO.getAppointmentTime());
                    entity.setPurpose(appointmentDTO.getPurpose());
                    return toDTO(appointmentRepository.save(entity));
                })
                .orElse(null);
    }

    @Override
    public void deleteAppointment(Long appointmentId) {
        appointmentRepository.deleteById(appointmentId);
    }

    private AppointmentDTO toDTO(AppointmentEntity entity) {
        return AppointmentDTO.builder()
                .appointmentId(entity.getAppointmentId())
                .userId(entity.getUserId())
                .appointmentDate(entity.getAppointmentDate())
                .appointmentTime(entity.getAppointmentTime())
                .purpose(entity.getPurpose())
                .build();
    }
}