package com.example.skin_back.appointment.dto;

import com.example.skin_back.appointment.entity.AppointmentEntity;
import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AppointmentDTO {
    private Long counselorId;
    private String appointmentDate;
    private String appointmentTime;
    private String appointmentEndTime;
    private String purpose;
    private String phone;
    private String birth;
    
    public AppointmentEntity toEntity(Long userId) {
        return AppointmentEntity.builder()
                .counselorId(counselorId)
                .userId(userId)
                .appointmentDate(java.time.LocalDate.parse(appointmentDate))
                .appointmentTime(appointmentTime)
                .appointmentEndTime(appointmentEndTime)
                .purpose(purpose)
                .birth(LocalDate.parse(birth))
                .phone(phone)  // ✅ 추가
                .status("예약완료")
                .build();
    }
}
