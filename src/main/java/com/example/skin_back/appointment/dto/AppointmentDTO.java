package com.example.skin_back.appointment.dto;

import com.example.skin_back.appointment.entity.AppointmentEntity;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AppointmentDTO {
    private Long counselorId;
    private String appointmentDate;
    private String appointmentTime;
    private String appointmentEndTime;
    private String purpose;

    public AppointmentEntity toEntity(Long userId) {
        return AppointmentEntity.builder()
                .counselorId(counselorId)
                .userId(userId)
                .appointmentDate(java.time.LocalDate.parse(appointmentDate))
                .appointmentTime(appointmentTime)
                .appointmentEndTime(appointmentEndTime)
                .purpose(purpose)
                .status("예약완료")
                .build();
    }
}
