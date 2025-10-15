package com.example.skin_back.user.dto;

import com.example.skin_back.user.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberInfoDTO {

    private String loginId; 
    private String username;
    private String birth;
    private String phoneNumber;
    private String email;

    public static MemberInfoDTO fromEntity(UserEntity userEntity) {
        return MemberInfoDTO.builder()
                .loginId(userEntity.getLoginId()) 
                .username(userEntity.getUsername())
                .birth(userEntity.getBirth()) 
                .phoneNumber(userEntity.getPhoneNumber())
                .email(userEntity.getEmail())
                .build();
    }
}