package com.example.skin_back.testappointment.service;

import com.example.skin_back.testappointment.dto.TestappointmentDTO;
import java.time.LocalDate;
import java.util.List;

public interface TestappointmentService {
    List<TestappointmentDTO> getCounselorSchedule(Long counselorId, LocalDate startDate, LocalDate endDate);
}