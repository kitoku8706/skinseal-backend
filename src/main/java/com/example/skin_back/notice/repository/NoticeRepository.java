package com.example.skin_back.notice.repository;

import com.example.skin_back.notice.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity, Long>, NoticeRepositoryCustom {
    // Custom native paged search implemented in NoticeRepositoryImpl
}