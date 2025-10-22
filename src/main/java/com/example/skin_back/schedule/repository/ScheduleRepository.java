package com.example.skin_back.schedule.repository;

import com.example.skin_back.schedule.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
    // 특정 상담사 일정 조회 (주별, 필요시 추가 파라미터로 확장 가능)
    List<ScheduleEntity> findByConsultantId(Integer consultantId);

    List<ScheduleEntity> findByDate(String date);

    List<ScheduleEntity> findByWeekDay(Integer weekDay);
}