package com.example.skin_back.appointment.controller;

import com.example.skin_back.appointment.dto.AppointmentDTO;
import com.example.skin_back.appointment.entity.AppointmentEntity;
import com.example.skin_back.appointment.service.AppointmentService;
import com.example.skin_back.user.config.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AppointmentController {

    private final AppointmentService appointmentService;

    /**
     * ✅ 예약 등록 (로그인 사용자만)
     */
    @PostMapping
    public ResponseEntity<?> createAppointment(
            @RequestBody AppointmentDTO dto,
            Authentication authentication
    ) {
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails user)) {
            return ResponseEntity.status(401).body("로그인 후 이용해주세요.");
        }

        try {
            appointmentService.saveAppointment(dto, user.getUserEntity().getUserId());
            return ResponseEntity.ok("예약이 완료되었습니다!");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("예약 처리 중 오류 발생: " + e.getMessage());
        }
    }

    /**
     * ✅ 날짜별 예약 목록 조회
     */
    @GetMapping("/date/{date}")
    public ResponseEntity<List<AppointmentEntity>> getAppointmentsByDate(@PathVariable("date") String date) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByDate(date));
    }

}
