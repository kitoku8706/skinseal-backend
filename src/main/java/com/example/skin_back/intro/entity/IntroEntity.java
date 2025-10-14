package com.example.skin_back.intro.entity;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "greeting", columnDefinition = "TEXT")
    private String greeting;

    @Column(name = "location", columnDefinition = "TEXT")
    private String location;

    @Column(name = "menu_type", length = 50)
    private String menuType;
}