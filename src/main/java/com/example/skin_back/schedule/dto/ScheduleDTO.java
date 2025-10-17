package com.example.skin_back.schedule.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleDTO {

    private Long scheduleId;
    private Integer consultantId;
    private Integer weekDay;
    private String timeSlot;
    private Boolean isAvailable;
    private String note;
}