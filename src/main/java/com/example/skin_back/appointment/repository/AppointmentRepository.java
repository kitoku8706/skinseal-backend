package com.example.skin_back.appointment.repository;

import com.example.skin_back.appointment.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
    // 필요시 커스텀 쿼리 추가
}
