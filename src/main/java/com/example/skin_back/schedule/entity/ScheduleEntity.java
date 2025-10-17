package com.example.skin_back.schedule.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ss_consultant_schedule")
public class ScheduleEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long scheduleId;

    @Column(name = "consultant_id", nullable = false)
    private Integer consultantId;

    @Column(name = "week_day", nullable = false)
    private Integer weekDay;

    @Column(name = "time_slot", nullable = false, length = 255)
    private String timeSlot;

    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable;

    @Column(name = "note", length = 255)
    private String note;
}