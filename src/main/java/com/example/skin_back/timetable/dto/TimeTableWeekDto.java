package com.example.skin_back.timetable.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeTableWeekDto {
    private Long counselorId;
    private String timetableDate;
    private String timetableTime;
    private String status;
}
