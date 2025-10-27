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

        // ✅ 1. 같은 상담사, 날짜, 시간대 중복 체크
        boolean timeConflict = appointmentRepository.existsByCounselorIdAndAppointmentDateAndAppointmentTime(
                dto.getCounselorId(),
                date,
                dto.getAppointmentTime()
        );

        if (timeConflict) {
            throw new IllegalStateException("예약된 내역이 있습니다. 예약은 1일 1회만 가능합니다.");
        }

        // ✅ 2. 동일 유저가 이미 예약한 경우 차단
        boolean userAlreadyReserved = appointmentRepository.existsByUserId(userId);
        if (userAlreadyReserved) {
            throw new IllegalStateException("이미 예약된 내역이 있습니다. 한 번만 예약할 수 있습니다.");
        }

        // ✅ 3. 예약 생성
        AppointmentEntity entity = dto.toEntity(userId);
        appointmentRepository.save(entity);
    }

    @Override
    public List<AppointmentEntity> getAppointmentsByDate(String date) {
        LocalDate localDate = LocalDate.parse(date);
        return appointmentRepository.findByAppointmentDate(localDate);
    }
}
