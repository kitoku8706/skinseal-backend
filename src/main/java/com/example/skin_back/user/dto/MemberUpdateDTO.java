package com.example.skin_back.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberUpdateDTO {

    private String phoneNumber;
    private String email;

    private String currentPassword;
    private String newPassword;
    
    public boolean isPasswordChangeRequest() {
        return newPassword != null && !newPassword.isEmpty();
    }
    
    
    
}