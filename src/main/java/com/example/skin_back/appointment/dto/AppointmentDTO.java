package com.example.skin_back.appointment.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentDTO {
    private Long appointmentId;
    
    @JsonProperty("user_id")
    private Long userId;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("appointment_date")
    private LocalDate appointmentDate;
    
    @JsonProperty("appointment_time")
    private String appointmentTime;
    
    private String purpose;
    
    @JsonProperty("consultant_id")
    private Long consultantId;
}