package com.worthmate.demo.mentor.entity;

import com.worthmate.demo.user.entity.UserEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "mentor_profiles")
public class MentorProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private UserEntity user;

    private String expertise;

    private Double pricePerHour;

    private Double trustScore;

    private String bio;

    // Getters & Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UserEntity getUser() { return user; }
    public void setUser(UserEntity user) { this.user = user; }

    public String getExpertise() { return expertise; }
    public void setExpertise(String expertise) { this.expertise = expertise; }

    public Double getPricePerHour() { return pricePerHour; }
    public void setPricePerHour(Double pricePerHour) { this.pricePerHour = pricePerHour; }

    public Double getTrustScore() { return trustScore; }
    public void setTrustScore(Double trustScore) { this.trustScore = trustScore; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
}
