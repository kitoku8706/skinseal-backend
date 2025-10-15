// src/main/java/com/example/skin_back/management/service/impl/ManagementServiceImpl.java
package com.example.skin_back.management.service;

import com.example.skin_back.management.dto.ManagementDTO;
import com.example.skin_back.management.entity.ManagementEntity;
import com.example.skin_back.management.repository.ManagementRepository;
import com.example.skin_back.management.service.ManagementService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagementServiceImpl implements ManagementService {

    private final ManagementRepository managementRepository;

    public ManagementServiceImpl(ManagementRepository managementRepository) {
        this.managementRepository = managementRepository;
    }

    @Override
    public List<ManagementDTO> findAllManagement() {
        return managementRepository.findAll().stream()
                .map(entity -> new ManagementDTO(
                        entity.getId(),
                        entity.getName(),
                        entity.getPosition(),
                        entity.getDescription(),
                        entity.getProfileImage(),
                        entity.getReservationLink()))
                .collect(Collectors.toList());
    }
}