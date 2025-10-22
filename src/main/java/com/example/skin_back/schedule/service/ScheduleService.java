package com.example.skin_back.schedule.service;

import com.example.skin_back.schedule.dto.ScheduleDTO;

import java.util.List;

public interface ScheduleService {

    ScheduleDTO createSchedule(ScheduleDTO scheduleDTO);

    ScheduleDTO getSchedule(Long scheduleId);

    List<ScheduleDTO> getSchedulesByConsultantId(Integer consultantId);

    List<ScheduleDTO> getAllSchedules();

    List<ScheduleDTO> getSchedulesByDate(String date);

    ScheduleDTO updateSchedule(Long scheduleId, ScheduleDTO scheduleDTO);

    void deleteSchedule(Long scheduleId);
}