// src/main/java/com/example/skin_back/management/entity/ManagementEntity.java
package com.example.skin_back.management.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "management")
public class ManagementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;            // 관계자 이름

    private String position;        // 직위

    @Column(length = 500)
    private String description;     // 소개 및 경력

    @Column(name = "profile_image")
    private String profileImage;    // 프로필 이미지 경로

    @Column(name = "reservation_link")
    private String reservationLink; // 상담 예약 링크

    @Column(name = "contact_info")
    private String contactInfo;     // 연락처 (보안 고려)

    public ManagementEntity() {}

    // Getter / Setter

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getProfileImage() {
        return profileImage;
    }
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
    public String getReservationLink() {
        return reservationLink;
    }
    public void setReservationLink(String reservationLink) {
        this.reservationLink = reservationLink;
    }
    public String getContactInfo() {
        return contactInfo;
    }
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
}