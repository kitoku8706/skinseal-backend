package com.example.skin_back.appointment.controller;

import com.example.skin_back.appointment.dto.AppointmentDTO;
import com.example.skin_back.appointment.entity.AppointmentEntity;
import com.example.skin_back.appointment.service.AppointmentService;
import com.example.skin_back.user.config.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
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

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AppointmentDTO dto,
                                    Authentication authentication,
                                    HttpServletRequest request) {
        try {
            Long userId = null;

            if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails cud) {
                userId = cud.getUserEntity().getUserId();
            }

            if (userId == null) {
                return ResponseEntity.status(401).body("로그인 후 이용해주세요.");
            }

            appointmentService.saveAppointment(dto, userId);
            return ResponseEntity.ok("✅ 상담 예약이 완료되었습니다.");

        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("예약 처리 중 오류: " + e.getMessage());
        }
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<AppointmentEntity>> getByDate(@PathVariable String date) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByDate(date));
    }
}
