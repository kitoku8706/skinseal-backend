package com.example.skin_back.appointment.service;

import com.example.skin_back.appointment.dto.AppointmentDTO;
import com.example.skin_back.appointment.entity.AppointmentEntity;
import com.example.skin_back.appointment.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Override
    public void saveAppointment(AppointmentDTO dto, Long userId) {
        LocalDate date = LocalDate.parse(dto.getAppointmentDate());

        boolean exists = appointmentRepository.existsByCounselorIdAndAppointmentDateAndAppointmentTime(
                dto.getCounselorId(), date, dto.getAppointmentTime()
        );

        if (exists) {
            throw new IllegalStateException("해당 시간대에 이미 예약이 존재합니다.");
        }

        AppointmentEntity entity = dto.toEntity(userId);
        appointmentRepository.save(entity);
    }

    @Override
    public List<AppointmentEntity> getAppointmentsByDate(String date) {
        LocalDate localDate = LocalDate.parse(date);
        return appointmentRepository.findByAppointmentDate(localDate);
    }
}
