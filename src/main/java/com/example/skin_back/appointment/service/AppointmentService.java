package com.example.skin_back.appointment.service;

import com.example.skin_back.appointment.dto.AppointmentDTO;
import com.example.skin_back.appointment.entity.AppointmentEntity;
import java.util.List;

public interface AppointmentService {

    // ✅ 예약 생성
    void saveAppointment(AppointmentDTO dto, Long userId);

    // ✅ 날짜별 예약 조회
    List<AppointmentEntity> getAppointmentsByDate(String date);

    // ✅ 예약 취소
    void cancelAppointment(Long userId);
}