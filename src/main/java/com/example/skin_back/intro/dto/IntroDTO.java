package com.example.skin_back.intro.dto;

import lombok.*;

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