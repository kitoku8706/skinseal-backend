package com.example.skin_back.check.service;

import com.example.skin_back.appointment.entity.AppointmentEntity;
import com.example.skin_back.appointment.repository.AppointmentRepository;
import com.example.skin_back.check.entity.CheckEntity;
import com.example.skin_back.check.repository.CheckRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckServiceImpl implements CheckService {

    private final CheckRepository checkRepository;
    private final AppointmentRepository appointmentRepository;

    /**
     * ✅ 최신 예약 조회 (취소된 예약 제외)
     */
    @Override
    public CheckEntity getLatestCheck(Long userId) {
        return checkRepository.findTopByUserIdAndStatusNotOrderByAppointmentDateDesc(userId, "CANCELLED")
                .orElse(null);
    }

    /**
     * ✅ 예약 취소 (상태만 변경)
     */
    @Transactional
    @Override
    public void cancelCheck(Long appointmentId) {
        AppointmentEntity appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 예약을 찾을 수 없습니다. appointmentId=" + appointmentId));

        if ("CANCELLED".equalsIgnoreCase(appointment.getStatus())) {
            throw new IllegalStateException("이미 취소된 예약입니다.");
        }

        appointment.setStatus("CANCELLED");
    }
}

