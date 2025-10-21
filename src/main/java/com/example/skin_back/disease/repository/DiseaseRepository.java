package com.example.skin_back.disease.repository;

import com.example.skin_back.disease.entity.DiseaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DiseaseRepository extends JpaRepository<DiseaseEntity, Long> {
    // ID만 필요한 경우 (경량 쿼리)
    @Query("SELECT d.diseaseId FROM DiseaseEntity d ORDER BY d.diseaseId ASC")
    List<Long> findAllIds();
    
    // 이름만 필요한 경우 (경량 쿼리)
    @Query("SELECT d.diseaseId, d.diseaseName FROM DiseaseEntity d ORDER BY d.diseaseId ASC")
    List<Object[]> findAllNamesWithIds();
}
