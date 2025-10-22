package com.example.skin_back.schedule.controller;

import com.example.skin_back.schedule.dto.ScheduleDTO;
import com.example.skin_back.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleDTO> createSchedule(@RequestBody ScheduleDTO dto) {
        return ResponseEntity.ok(scheduleService.createSchedule(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDTO> getSchedule(@PathVariable Long id) {
        ScheduleDTO dto = scheduleService.getSchedule(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/consultant/{consultantId}")
    public ResponseEntity<List<ScheduleDTO>> getSchedulesByConsultant(@PathVariable Integer consultantId) {
        return ResponseEntity.ok(scheduleService.getSchedulesByConsultantId(consultantId));
    }

    @GetMapping
    public ResponseEntity<List<ScheduleDTO>> getAllSchedules() {
        return ResponseEntity.ok(scheduleService.getAllSchedules());
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<ScheduleDTO>> getSchedulesByDate(@PathVariable String date) {
        List<ScheduleDTO> schedules = scheduleService.getSchedulesByDate(date);
        return ResponseEntity.ok(schedules);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleDTO> updateSchedule(@PathVariable Long id, @RequestBody ScheduleDTO dto) {
        ScheduleDTO updated = scheduleService.updateSchedule(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
}