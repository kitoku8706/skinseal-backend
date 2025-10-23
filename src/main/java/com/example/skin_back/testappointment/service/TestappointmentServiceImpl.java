package com.example.skin_back.testappointment.service;

import com.example.skin_back.testappointment.dto.TestappointmentDTO;
import com.example.skin_back.testappointment.entity.TestappointmentEntity;
import com.example.skin_back.testappointment.repository.TestappointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor 
@Transactional(readOnly = true) 
public class TestappointmentServiceImpl implements TestappointmentService {

    private final TestappointmentRepository testappointmentRepository;

    @Override
    public List<TestappointmentDTO> getCounselorSchedule(Long counselorId, LocalDate startDate, LocalDate endDate) {
        
        // 1. Repository를 사용하여 DB에서 예약 Entity 목록을 조회합니다.
        List<TestappointmentEntity> entities = testappointmentRepository
                .findByCounselorIdAndAppointmentDateBetweenOrderByAppointmentDateAscAppointmentTimeAsc(
                        counselorId, 
                        startDate, 
                        endDate
                );

        // 2. 조회된 Entity 목록을 Stream API를 사용하여 DTO 목록으로 변환하여 반환합니다.
        return entities.stream()
                .map(TestappointmentDTO::toDTO) 
                .collect(Collectors.toList());
    }
}