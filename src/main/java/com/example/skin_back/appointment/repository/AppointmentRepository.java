package com.example.skin_back.appointment.repository;

import com.example.skin_back.appointment.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * 📘 AppointmentRepository
 * - 상담 예약 데이터를 DB에서 조회 / 저장 / 중복검사하는 레포지토리 인터페이스
 * - JpaRepository를 상속받아 기본 CRUD 기능 자동 제공
 */
@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {

    boolean existsByCounselorIdAndAppointmentDateAndAppointmentTime(
            Long counselorId, LocalDate date, String time
    );

    boolean existsByUserId(Long userId); // ✅ 추가 (한 유저당 1개 예약만 가능)

    List<AppointmentEntity> findByAppointmentDate(LocalDate date);
}
