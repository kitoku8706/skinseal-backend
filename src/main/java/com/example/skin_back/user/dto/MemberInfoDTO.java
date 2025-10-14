package com.example.skin_back.user.dto;

import com.example.skin_back.user.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberInfoDTO {

    private String username;
    private String phoneNumber;
    private String email;

    // UserEntity를 MemberInfoDto로 변환하는 정적 메서드
    public static MemberInfoDTO fromEntity(UserEntity userEntity) {
        return MemberInfoDTO.builder()
                .username(userEntity.getUsername())
                .phoneNumber(userEntity.getPhoneNumber())
                .email(userEntity.getEmail())
                .build();
    }
}