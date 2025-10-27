package com.example.skin_back.check.repository;

import com.example.skin_back.check.entity.CheckEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CheckRepository extends JpaRepository<CheckEntity, Long> {
    Optional<CheckEntity> findTopByUserIdOrderByAppointmentDateDesc(Long userId);
}
