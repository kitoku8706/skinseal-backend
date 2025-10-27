package com.example.skin_back.timetable.service;

import com.example.skin_back.timetable.dto.TimeTableWeekDto;

import java.time.LocalDate;
import java.util.List;

public interface TimeTableService {
    List<TimeTableWeekDto> getTimeTableByWeek(LocalDate startDate);
}
