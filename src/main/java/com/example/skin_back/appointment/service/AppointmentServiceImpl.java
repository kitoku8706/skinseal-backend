package com.example.skin_back.appointment.service;

import com.example.skin_back.appointment.dto.AppointmentDTO;
import com.example.skin_back.appointment.entity.AppointmentEntity;
import com.example.skin_back.appointment.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    /**
     * ✅ 상담 예약 생성
     */
    @Override
    public void saveAppointment(AppointmentDTO dto, Long userId) {
        LocalDate date = LocalDate.parse(dto.getAppointmentDate());

        // 1️⃣ 같은 상담사 / 같은 날짜 / 같은 시간대 중복 방지
        boolean timeConflict = appointmentRepository.existsByCounselorIdAndAppointmentDateAndAppointmentTime(
                dto.getCounselorId(), date, dto.getAppointmentTime()
        );
        if (timeConflict) {
            throw new IllegalStateException("이미 해당 상담사 시간대에 예약이 존재합니다.");
        }

        // 2️⃣ 같은 날짜에 이미 예약한 유저는 중복 방지
        boolean userConflict = appointmentRepository.existsByUserIdAndAppointmentDate(userId, date);
        if (userConflict) {
            throw new IllegalStateException("같은 날짜에 이미 예약이 있습니다. 다른 날짜를 선택해주세요.");
        }

        // 3️⃣ 정상 저장
        AppointmentEntity entity = dto.toEntity(userId);
        appointmentRepository.save(entity);
    }

    /**
     * ✅ 날짜별 예약 조회
     */
    @Override
    public List<AppointmentEntity> getAppointmentsByDate(String date) {
        LocalDate localDate = LocalDate.parse(date);
        return appointmentRepository.findByAppointmentDate(localDate);
    }

    /**
     * ✅ 예약 취소
     */
    @Override
    @Transactional
    public void cancelAppointment(Long userId) {
        appointmentRepository.deleteByUserId(userId);
        appointmentRepository.flush(); // 즉시 반영
    }
}
