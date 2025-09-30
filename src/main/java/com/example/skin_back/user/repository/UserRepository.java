package com.example.skin_back.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.skin_back.user.entity.UserEntity;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
	
}