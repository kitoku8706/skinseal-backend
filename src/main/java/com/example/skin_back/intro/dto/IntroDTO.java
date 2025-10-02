package com.example.skin_back.intro.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IntroDTO {
    private Long infoId;
    private String greeting;
    private String location;
    private String menuType;
}