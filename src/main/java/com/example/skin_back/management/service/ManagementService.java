// src/main/java/com/example/skin_back/management/service/ManagementService.java
package com.example.skin_back.management.service;

import com.example.skin_back.management.dto.ManagementDTO;

import java.util.List;

public interface ManagementService {
    List<ManagementDTO> findAllManagement();
}