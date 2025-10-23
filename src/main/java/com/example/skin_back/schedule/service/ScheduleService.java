package com.example.skin_back.schedule.service;

import com.example.skin_back.schedule.dto.ScheduleDTO;
import java.util.List;

public interface ScheduleService {
    List<ScheduleDTO> getAllSchedules();
    List<ScheduleDTO> getSchedulesByDate(String date);
    List<ScheduleDTO> getSchedulesByCounselorId(Long counselorId);
    List<ScheduleDTO> getSchedulesByCounselorIdAndDate(Long counselorId, String date);
    List<ScheduleDTO> getSchedulesByCounselorName(String counselorName);
    List<ScheduleDTO> getSchedulesByCounselorNameAndDate(String counselorName, String date);
    ScheduleDTO getScheduleById(Long id);
    ScheduleDTO createSchedule(ScheduleDTO scheduleDTO);
    ScheduleDTO updateSchedule(Long id, ScheduleDTO scheduleDTO);
    void deleteSchedule(Long id);
    void updateAvailability(Long id, Boolean available);
}