package com.example.skin_back.user.config;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.skin_back.user.entity.UserEntity;

import lombok.Getter;

@Getter

public class CustomUserDetails implements UserDetails {

    // final 필드
    private final UserEntity user; 

    // ✅ 오류 해결을 위한 생성자 (UserEntity를 받아 필드를 초기화)
    public CustomUserDetails(UserEntity user) {
        this.user = user;
    }
    
    // 추가 getter: 서비스 레이어에서 실제 UserEntity 객체를 필요로 할 때 사용합니다.
    public UserEntity getUserEntity() {
        return user;
    }

    // --- UserDetails 인터페이스 구현 ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 실제 애플리케이션에서는 UserEntity의 권한 필드(예: role)를 읽어와야 합니다.
        // 현재는 단순 예시로 'ROLE_USER' 권한만 부여한다고 가정합니다.
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        // UserEntity에서 암호화된 비밀번호를 반환
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        // 사용자명 (로그인 시 사용되는 ID, 이메일 등) 반환
        return user.getEmail(); // UserEntity에 'getEmail()'이 있다고 가정
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
