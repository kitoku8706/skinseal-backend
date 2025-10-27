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

    private final CheckRepository checkRepository; // ✅ 조회용
    private final AppointmentRepository appointmentRepository; // ✅ 취소용 (실제 데이터 조작)

    /**
     * ✅ 최신 예약 조회 (유저별)
     * - appointment_date 기준으로 내림차순 정렬 후 1개 반환
     */
    @Override
    public CheckEntity getLatestCheck(Long userId) {
        return checkRepository.findTopByUserIdOrderByAppointmentDateDesc(userId)
                .orElse(null);
    }

    /**
     * ✅ 예약 취소
     * - AppointmentEntity를 직접 조작하여 상태 변경
     */
    @Transactional
    @Override
    public void cancelCheck(Long appointmentId) {
        System.out.println("✅ [cancelCheck] 요청 도착 - appointmentId: " + appointmentId);

        // 1️⃣ appointmentId 로 예약 조회
        AppointmentEntity appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 예약을 찾을 수 없습니다. appointmentId=" + appointmentId));

        // 2️⃣ 상태 확인
        String currentStatus = appointment.getStatus();
        System.out.println("현재 상태: " + currentStatus);

        if ("CANCELLED".equalsIgnoreCase(currentStatus)) {
            System.out.println("⚠️ 이미 취소된 예약입니다.");
            throw new IllegalStateException("이미 취소된 예약입니다.");
        }

        // 3️⃣ 상태 변경
        appointment.setStatus("CANCELLED");

        // 4️⃣ save 호출 없이 자동 반영됨 (Transactional + Dirty Checking)
        System.out.println("✅ 예약 취소 완료 (상태: CANCELLED)");
    }
}
