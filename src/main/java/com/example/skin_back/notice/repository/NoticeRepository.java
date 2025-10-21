package com.example.skin_back.notice.repository;

import com.example.skin_back.notice.entity.NoticeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {
    // 첨부파일을 포함한 조회 (필요시에만 사용)
    @EntityGraph(attributePaths = {"attachments"})
    @Query("SELECT n FROM NoticeEntity n WHERE n.noticeId = :id")
    Optional<NoticeEntity> findByIdWithAttachments(Long id);
    
    // 기본 목록 조회 (첨부파일 제외)
    @Override
    @Query("SELECT n FROM NoticeEntity n ORDER BY n.createdAt DESC")
    List<NoticeEntity> findAll();
    
    // 페이지네이션 + 정렬
    @Query("SELECT n FROM NoticeEntity n ORDER BY n.createdAt DESC")
    Page<NoticeEntity> findAllOrderByCreatedAtDesc(Pageable pageable);
    
    // 키워드 검색 (제목 또는 내용)
    @Query("SELECT n FROM NoticeEntity n WHERE " +
           "(:keyword IS NULL OR LOWER(n.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(n.content) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
           "AND (:type IS NULL OR n.type = :type) " +
           "ORDER BY n.createdAt DESC")
    Page<NoticeEntity> searchNotices(String keyword, String type, Pageable pageable);
}