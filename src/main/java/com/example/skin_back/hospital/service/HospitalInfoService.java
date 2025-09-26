package com.example.skin_back.hospital.service;

import com.example.skin_back.hospital.dto.HospitalInfoDTO;
import java.util.List;

public interface HospitalInfoService {
    HospitalInfoDTO createHospitalInfo(HospitalInfoDTO hospitalInfoDTO);
    HospitalInfoDTO getHospitalInfoById(Long infoId);
    List<HospitalInfoDTO> getAllHospitalInfos();
    HospitalInfoDTO updateHospitalInfo(Long infoId, HospitalInfoDTO hospitalInfoDTO);
    void deleteHospitalInfo(Long infoId);
}
