package com.example.skin_back.check.controller;

import com.example.skin_back.check.entity.CheckEntity;
import com.example.skin_back.check.service.CheckService;
import com.example.skin_back.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/check")
@RequiredArgsConstructor
public class CheckController {

    private final CheckService checkService;

    /**
     * ✅ 최신 예약 내역 조회 (로그인된 유저 기준)
     */
    @GetMapping("/latest")
    public ResponseEntity<CheckEntity> getLatestCheck(
            @AuthenticationPrincipal(expression = "user") UserEntity user) {

        // 🔐 로그인 정보가 없으면 401 Unauthorized 반환
        if (user == null) {
            return ResponseEntity.status(401).build();
        }

        // ✅ 최신 예약 조회
        CheckEntity check = checkService.getLatestCheck(user.getId());
        return ResponseEntity.ok(check);
    }

    /**
     * ✅ 예약 취소 기능
     * - URL: PUT /api/check/{appointmentId}/cancel
     * - 예: /api/check/17/cancel
     */
    @PutMapping("/{appointmentId}/cancel")
    public ResponseEntity<String> cancelCheck(
            @PathVariable("appointmentId") Long appointmentId, // ✅ 이름 명시로 에러 방지
            @AuthenticationPrincipal(expression = "user") UserEntity user) {

        // 🔐 로그인하지 않은 사용자 예외 처리
        if (user == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        try {
            // ✅ 예약 취소 수행
            checkService.cancelCheck(appointmentId);
            return ResponseEntity.ok("예약이 취소되었습니다.");

        } catch (Exception e) {
            // ⚠️ 예외 발생 시 로그 확인용 메시지 출력
            e.printStackTrace();
            return ResponseEntity.status(500).body("예약 취소 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}
