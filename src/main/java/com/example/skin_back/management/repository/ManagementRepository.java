// src/main/java/com/example/skin_back/management/repository/ManagementRepository.java
package com.example.skin_back.management.repository;

import com.example.skin_back.management.entity.ManagementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagementRepository extends JpaRepository<ManagementEntity, Long> {
}