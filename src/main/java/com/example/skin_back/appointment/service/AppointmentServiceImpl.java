package com.example.skin_back.appointment.service;

import com.example.skin_back.appointment.dto.AppointmentDTO;
import com.example.skin_back.appointment.entity.AppointmentEntity;
import com.example.skin_back.appointment.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Slf4j
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
        log.info("✅ 예약 저장 완료: userId={}, date={}, time={}", userId, dto.getAppointmentDate(), dto.getAppointmentTime());
    }

    @Override
    public List<AppointmentEntity> getAppointmentsByDate(String dateStr) {
        LocalDate date = LocalDate.parse(dateStr);
        return appointmentRepository.findByAppointmentDate(date);
    }
}
