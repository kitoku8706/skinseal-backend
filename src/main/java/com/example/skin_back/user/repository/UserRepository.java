package com.example.skin_back.user.repository;

import com.example.skin_back.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// Long 대신 UserEntity의 실제 Primary Key 타입(오류 메시지에서 주장하는 'String')으로 변경합니다.
public interface UserRepository extends JpaRepository<UserEntity, String> {
    
    // 사용자명(이메일)으로 조회 등 커스텀 메서드 추가 가능
    UserEntity findByEmail(String email);
}
