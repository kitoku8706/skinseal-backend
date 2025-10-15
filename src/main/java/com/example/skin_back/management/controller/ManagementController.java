// src/main/java/com/example/skin_back/management/controller/ManagementController.java
package com.example.skin_back.management.controller;

import com.example.skin_back.management.dto.ManagementDTO;
import com.example.skin_back.management.service.ManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 관리 정보 관련 REST API 컨트롤러입니다.
 */
@RestController
@RequestMapping("/management/api")
public class ManagementController {

    private final ManagementService managementService;

    @Autowired
    public ManagementController(ManagementService managementService) {
        this.managementService = managementService;
    }

    /**
     * 모든 관리 정보를 리스트로 반환합니다.
     * @return 관리 DTO 리스트
     */
    @GetMapping
    public List<ManagementDTO> getAllManagement() {
        return managementService.findAllManagement();
    }
}