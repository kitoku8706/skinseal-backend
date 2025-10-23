package com.example.skin_back.testappointment.dto;

import com.example.skin_back.testappointment.entity.TestappointmentEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class TestappointmentDTO {
    
    private Long appointmentId;
    private Long userId;
    private Long counselorId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String purpose;
    private LocalDateTime createdAt;
    private String status;

    public TestappointmentEntity toEntity() {
        return TestappointmentEntity.builder()
                .userId(this.userId)
                .counselorId(this.counselorId)
                .appointmentDate(this.appointmentDate)
                .appointmentTime(this.appointmentTime)
                .purpose(this.purpose)
                .build();
    }

    public static TestappointmentDTO toDTO(TestappointmentEntity entity) {
        return TestappointmentDTO.builder()
                .appointmentId(entity.getAppointmentId())
                .userId(entity.getUserId())
                .counselorId(entity.getCounselorId())
                .appointmentDate(entity.getAppointmentDate())
                .appointmentTime(entity.getAppointmentTime())
                .purpose(entity.getPurpose())
                .createdAt(entity.getCreatedAt())
                .status(entity.getStatus())
                .build();
    }
}