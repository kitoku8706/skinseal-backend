package com.example.skin_back.timetable.repository;

import com.example.skin_back.timetable.entity.TimeTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TimeTableRepository extends JpaRepository<TimeTableEntity, Long> {

    // ✅ 주간(7일) 데이터 조회
    List<TimeTableEntity> findByAppointmentDateBetween(LocalDate startDate, LocalDate endDate);
}
