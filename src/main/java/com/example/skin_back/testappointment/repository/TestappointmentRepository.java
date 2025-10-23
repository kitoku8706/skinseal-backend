package com.example.skin_back.testappointment.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.skin_back.testappointment.entity.TestappointmentEntity;


@Repository
public interface TestappointmentRepository extends JpaRepository<TestappointmentEntity, Long> {
    List<TestappointmentEntity> findByCounselorIdAndAppointmentDateBetweenOrderByAppointmentDateAscAppointmentTimeAsc(
        Long counselorId, 
        LocalDate startDate, 
        LocalDate endDate
    );
}