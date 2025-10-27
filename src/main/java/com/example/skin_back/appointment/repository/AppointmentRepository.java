package com.example.skin_back.appointment.repository;

import com.example.skin_back.appointment.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {

    // ✅ 상담사, 날짜, 시간 중복 예약 방지
    boolean existsByCounselorIdAndAppointmentDateAndAppointmentTime(
            Long counselorId, LocalDate date, String time
    );

    // ✅ 같은 유저가 같은 날짜에 예약한 적이 있는지 (시간 상관없이)
    boolean existsByUserIdAndAppointmentDate(Long userId, LocalDate date);

    // ✅ 날짜별 예약 조회
    List<AppointmentEntity> findByAppointmentDate(LocalDate date);

    // ✅ 예약 취소 (유저 ID 기준 삭제)
    void deleteByUserId(Long userId);
}
