package com.example.skin_back.testappointment.controller;

import com.example.skin_back.testappointment.dto.TestappointmentDTO;
import com.example.skin_back.testappointment.service.TestappointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/tests") 
@RequiredArgsConstructor
public class TestappointmentController {

    private final TestappointmentService testappointmentService;

    @GetMapping("/schedule/{counselorId}")
    public ResponseEntity<List<TestappointmentDTO>> getCounselorSchedule(
            // **⭐ 수정된 부분: PathVariable에 이름을 명시합니다. ⭐**
            @PathVariable("counselorId") Long counselorId, 
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {

        log.info("상담 시간표 조회 요청: Counselor ID={}, From {} to {}", counselorId, startDate, endDate);

        // ... 서비스 호출 로직은 동일
        List<TestappointmentDTO> schedule = testappointmentService.getCounselorSchedule(
                counselorId, 
                startDate, 
                endDate
        );

        return ResponseEntity.ok(schedule);
    }
}