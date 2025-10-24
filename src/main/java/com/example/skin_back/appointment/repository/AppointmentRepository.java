package com.example.skin_back.appointment.repository;

import com.example.skin_back.appointment.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
    List<AppointmentEntity> findByAppointmentDate(LocalDate date);
    boolean existsByCounselorIdAndAppointmentDateAndAppointmentTime(Long counselorId, LocalDate date, String time);
}
