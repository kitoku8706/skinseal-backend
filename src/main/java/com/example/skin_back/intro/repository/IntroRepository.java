package com.example.skin_back.intro.repository;

import com.example.skin_back.intro.entity.IntroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IntroRepository extends JpaRepository<IntroEntity, Long> {
    List<IntroEntity> findByMenuType(String menuType);
}