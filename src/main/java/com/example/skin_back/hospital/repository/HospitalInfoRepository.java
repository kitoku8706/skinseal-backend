package com.example.skin_back.hospital.repository;

import com.example.skin_back.hospital.entity.HospitalInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalInfoRepository extends JpaRepository<HospitalInfoEntity, Long> {
    // 필요시 커스텀 쿼리 추가
}
