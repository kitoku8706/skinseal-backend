package com.example.skin_back.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.skin_back.user.entity.UserEntity;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
	Optional<UserEntity> findByLoginId(String username);
	boolean existsByLoginId(String username);
	Optional<UserEntity> findByEmail(String email);
	boolean existsByEmail(String email);
}