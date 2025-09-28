package com.example.skin_back.hospital.controller;

import com.example.skin_back.hospital.dto.HospitalInfoDTO;
import com.example.skin_back.hospital.service.HospitalInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/hospital-info")
@RequiredArgsConstructor
public class HospitalInfoController {
    private final HospitalInfoService hospitalInfoService;

    @PostMapping
    public ResponseEntity<HospitalInfoDTO> create(@RequestBody HospitalInfoDTO dto) {
        return ResponseEntity.ok(hospitalInfoService.createHospitalInfo(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HospitalInfoDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(hospitalInfoService.getHospitalInfoById(id));
    }

    @GetMapping
    public ResponseEntity<List<HospitalInfoDTO>> getAll() {
        return ResponseEntity.ok(hospitalInfoService.getAllHospitalInfos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<HospitalInfoDTO> update(@PathVariable Long id, @RequestBody HospitalInfoDTO dto) {
        return ResponseEntity.ok(hospitalInfoService.updateHospitalInfo(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        hospitalInfoService.deleteHospitalInfo(id);
        return ResponseEntity.noContent().build();
    }
}