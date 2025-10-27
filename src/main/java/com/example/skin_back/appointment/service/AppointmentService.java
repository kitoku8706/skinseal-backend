package com.example.skin_back.appointment.service;

import com.example.skin_back.appointment.dto.AppointmentDTO;
import com.example.skin_back.appointment.entity.AppointmentEntity;
import java.util.List;

public interface AppointmentService {
    void saveAppointment(AppointmentDTO dto, Long userId);
    List<AppointmentEntity> getAppointmentsByDate(String date);
}
