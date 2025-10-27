package com.example.skin_back.timetable.controller;

import com.example.skin_back.timetable.dto.TimeTableWeekDto;
import com.example.skin_back.timetable.service.TimeTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/timetable")
@RequiredArgsConstructor
public class TimeTableController {

    private final TimeTableService timeTableService;

    @GetMapping("/week")
    public ResponseEntity<List<TimeTableWeekDto>> getTimeTableByWeek(
            @RequestParam("startDate") String startDate   // ✅ 반드시 이름 명시
    ) {
        System.out.println("📅 주간 시간표 조회 시작일: " + startDate);
        return ResponseEntity.ok(timeTableService.getTimeTableByWeek(LocalDate.parse(startDate)));
    }

}
