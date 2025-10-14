package com.example.skin_back.management.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "management")
public class ManagementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String position;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "profileimage", length = 255)
    private String profileImage;

    @Column(name = "contact_info", length = 100)
    private String contactInfo;

    public ManagementEntity() {}

    public ManagementEntity(String name, String position, String description, String profileImage, String contactInfo) {
        this.name = name;
        this.position = position;
        this.description = description;
        this.profileImage = profileImage;
        this.contactInfo = contactInfo;
    }

    // getter와 setter 메소드들
    public Long getId() {
        return id;
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

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
}