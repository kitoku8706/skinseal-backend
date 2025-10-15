// src/main/java/com/example/skin_back/management/dto/ManagementDTO.java
package com.example.skin_back.management.dto;

public class ManagementDTO {

    private Long id;
    private String name;
    private String position;
    private String description;
    private String profileImage;
    private String reservationLink;

    public ManagementDTO(Long id, String name, String position, String description,
                             String profileImage, String reservationLink) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.description = description;
        this.profileImage = profileImage;
        this.reservationLink = reservationLink;
    }

    // Getters and Setters

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
}