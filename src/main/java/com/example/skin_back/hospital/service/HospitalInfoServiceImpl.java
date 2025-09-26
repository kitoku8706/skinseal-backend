package com.example.skin_back.hospital.service;

import com.example.skin_back.hospital.dto.HospitalInfoDTO;
import com.example.skin_back.hospital.entity.HospitalInfoEntity;
import com.example.skin_back.hospital.repository.HospitalInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HospitalInfoServiceImpl implements HospitalInfoService {
    private final HospitalInfoRepository hospitalInfoRepository;

    @Override
    public HospitalInfoDTO createHospitalInfo(HospitalInfoDTO hospitalInfoDTO) {
        HospitalInfoEntity entity = HospitalInfoEntity.builder()
                .greeting(hospitalInfoDTO.getGreeting())
                .location(hospitalInfoDTO.getLocation())
                .build();
        HospitalInfoEntity saved = hospitalInfoRepository.save(entity);
        return toDTO(saved);
    }

    @Override
    public HospitalInfoDTO getHospitalInfoById(Long infoId) {
        return hospitalInfoRepository.findById(infoId)
                .map(this::toDTO)
                .orElse(null);
    }

    @Override
    public List<HospitalInfoDTO> getAllHospitalInfos() {
        return hospitalInfoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public HospitalInfoDTO updateHospitalInfo(Long infoId, HospitalInfoDTO hospitalInfoDTO) {
        return hospitalInfoRepository.findById(infoId)
                .map(entity -> {
                    entity.setGreeting(hospitalInfoDTO.getGreeting());
                    entity.setLocation(hospitalInfoDTO.getLocation());
                    return toDTO(hospitalInfoRepository.save(entity));
                })
                .orElse(null);
    }

    @Override
    public void deleteHospitalInfo(Long infoId) {
        hospitalInfoRepository.deleteById(infoId);
    }

    private HospitalInfoDTO toDTO(HospitalInfoEntity entity) {
        return HospitalInfoDTO.builder()
                .infoId(entity.getInfoId())
                .greeting(entity.getGreeting())
                .location(entity.getLocation())
                .build();
    }
}
