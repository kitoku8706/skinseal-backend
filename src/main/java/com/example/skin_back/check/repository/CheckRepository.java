package com.example.skin_back.check.repository;

import com.example.skin_back.check.entity.CheckEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CheckRepository extends JpaRepository<CheckEntity, Long> {

    // ✅ 최신 예약 중 "취소되지 않은" 예약만 조회
    Optional<CheckEntity> findTopByUserIdAndStatusNotOrderByAppointmentDateDesc(Long userId, String status);
}
