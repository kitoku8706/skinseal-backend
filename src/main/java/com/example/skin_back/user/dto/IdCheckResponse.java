package com.example.skin_back.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class IdCheckResponse {
    
    private boolean exists; 
}