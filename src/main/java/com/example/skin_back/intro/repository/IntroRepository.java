package com.example.skin_back.intro.repository;

import com.example.skin_back.intro.entity.IntroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IntroRepository extends JpaRepository<IntroEntity, Long> {
    // 메뉴 타입별 조회를 위한 메소드 추가
    List<IntroEntity> findByMenuType(String menuType);
}