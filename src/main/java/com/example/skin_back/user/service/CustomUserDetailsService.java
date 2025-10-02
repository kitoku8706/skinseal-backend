package com.example.skin_back.user.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.skin_back.user.config.CustomUserDetails;
import com.example.skin_back.user.entity.UserEntity;
import com.example.skin_back.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    
	private final UserRepository userRepository;
	
    // Spring Security의 표준 메서드 (일반적인 Username/Password 로그인 시 사용)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
        
        return new CustomUserDetails(userEntity);
    }
    
    // 💥 JWT 필터에서 사용할 메서드: userId(Long)로 사용자 정보 로드
    public UserDetails loadUserById(Long userId) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + userId));
        
        return new CustomUserDetails(userEntity);
    }
}
