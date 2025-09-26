package com.example.skin_back.appointment.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "SS_APPOINTMENT")
public class AppointmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appointment_seq_generator")
    @SequenceGenerator(name = "appointment_seq_generator", sequenceName = "APPOINTMENT_ID_SEQ", allocationSize = 1)
    @Column(name = "APPOINTMENT_ID")
    private Long appointmentId;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "APPOINTMENT_DATE", nullable = false)
    private LocalDate appointmentDate;

    @Column(name = "APPOINTMENT_TIME", nullable = false)
    private LocalTime appointmentTime;

    @Column(name = "PURPOSE")
    private String purpose;
}