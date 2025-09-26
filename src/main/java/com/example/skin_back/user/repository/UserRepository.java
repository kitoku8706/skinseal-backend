package com.example.skin_back.user.repository;

import com.example.skin_back.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // 사용자명으로 조회 등 커스텀 메서드 추가 가능
    UserEntity findByUsername(String username);
}
