package com.example.skin_back.appointment.service;

import com.example.skin_back.appointment.dto.AppointmentDTO;
import java.util.List;

public interface AppointmentService {
    AppointmentDTO createAppointment(AppointmentDTO appointmentDTO);
    AppointmentDTO getAppointmentById(Long appointmentId);
    List<AppointmentDTO> getAllAppointments();
    AppointmentDTO updateAppointment(Long appointmentId, AppointmentDTO appointmentDTO);
    void deleteAppointment(Long appointmentId);
}
