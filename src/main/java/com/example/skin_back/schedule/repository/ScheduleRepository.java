package com.example.skin_back.schedule.repository;

import com.example.skin_back.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByDate(String date);
    List<Schedule> findByCounselorId(Long counselorId);
    List<Schedule> findByCounselorIdAndDate(Long counselorId, String date);
    List<Schedule> findByCounselor_name(String counselorName);
    List<Schedule> findByCounselor_nameAndDate(String counselorName, String date);
}