package com.example.skin_back.intro.entity;

import jakarta.persistence.*;
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
@Entity
@Table(name = "intro")
public class IntroEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "info_id")
    private Long infoId;

    @Column(name = "greeting")
    private String greeting;

    @Column(name = "location")
    private String location;
    
    // 메뉴 구분을 위한 필드 (회사소개, 관계자 소개, 오시는 길)
    @Column(name = "menu_type")
    private String menuType;
}