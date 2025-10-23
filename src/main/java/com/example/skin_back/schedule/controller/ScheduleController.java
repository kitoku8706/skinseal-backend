package com.example.skin_back.schedule.controller;

import com.example.skin_back.schedule.dto.ScheduleDTO;
import com.example.skin_back.schedule.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@CrossOrigin(origins = "*")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // 특정 날짜의 모든 상담사 스케줄 조회 (프론트엔드 URL과 매칭: /api/schedules/date/{date})
    @GetMapping("/date/{date}")
    public ResponseEntity<List<ScheduleDTO>> getSchedulesByDate(@PathVariable String date) {
        List<ScheduleDTO> schedules = scheduleService.getSchedulesByDate(date);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }
    
    // 모든 스케줄 조회
    @GetMapping
    public ResponseEntity<List<ScheduleDTO>> getAllSchedules() {
        List<ScheduleDTO> schedules = scheduleService.getAllSchedules();
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    // ID로 스케줄 조회
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDTO> getScheduleById(@PathVariable Long id) {
        ScheduleDTO schedule = scheduleService.getScheduleById(id);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    // 상담사 ID로 스케줄 조회
    @GetMapping("/counselor-id/{counselorId}")
    public ResponseEntity<List<ScheduleDTO>> getSchedulesByCounselorId(@PathVariable Long counselorId) {
        List<ScheduleDTO> schedules = scheduleService.getSchedulesByCounselorId(counselorId);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    // 상담사 ID와 날짜로 스케줄 조회
    @GetMapping("/counselor-id/{counselorId}/date/{date}")
    public ResponseEntity<List<ScheduleDTO>> getSchedulesByCounselorIdAndDate(
            @PathVariable Long counselorId, @PathVariable String date) {
        List<ScheduleDTO> schedules = scheduleService.getSchedulesByCounselorIdAndDate(counselorId, date);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    // 상담사 이름으로 스케줄 조회
    @GetMapping("/counselor-name/{counselorName}")
    public ResponseEntity<List<ScheduleDTO>> getSchedulesByCounselorName(@PathVariable String counselorName) {
        List<ScheduleDTO> schedules = scheduleService.getSchedulesByCounselorName(counselorName);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    // 상담사 이름과 날짜로 스케줄 조회
    @GetMapping("/counselor-name/{counselorName}/date/{date}")
    public ResponseEntity<List<ScheduleDTO>> getSchedulesByCounselorNameAndDate(
            @PathVariable String counselorName, @PathVariable String date) {
        List<ScheduleDTO> schedules = scheduleService.getSchedulesByCounselorNameAndDate(counselorName, date);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    // 스케줄 생성
    @PostMapping
    public ResponseEntity<ScheduleDTO> createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        ScheduleDTO createdSchedule = scheduleService.createSchedule(scheduleDTO);
        return new ResponseEntity<>(createdSchedule, HttpStatus.CREATED);
    }

    // 스케줄 수정
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleDTO> updateSchedule(@PathVariable Long id, @RequestBody ScheduleDTO scheduleDTO) {
        ScheduleDTO updatedSchedule = scheduleService.updateSchedule(id, scheduleDTO);
        return new ResponseEntity<>(updatedSchedule, HttpStatus.OK);
    }

    // 스케줄 가용성 업데이트
    @PatchMapping("/{id}/availability")
    public ResponseEntity<Void> updateAvailability(@PathVariable Long id, @RequestParam Boolean available) {
        scheduleService.updateAvailability(id, available);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 스케줄 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}