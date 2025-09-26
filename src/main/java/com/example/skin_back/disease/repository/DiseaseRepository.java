package com.example.skin_back.disease.repository;

import com.example.skin_back.disease.entity.DiseaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiseaseRepository extends JpaRepository<DiseaseEntity, Long> {
    // 필요시 커스텀 쿼리 추가
}
