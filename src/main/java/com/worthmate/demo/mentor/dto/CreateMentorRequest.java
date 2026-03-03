package com.worthmate.demo.mentor.dto;

public class CreateMentorRequest {

    private Long userId;
    private String expertise;
    private Double pricePerHour;
    private String bio;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getExpertise() { return expertise; }
    public void setExpertise(String expertise) { this.expertise = expertise; }

    public Double getPricePerHour() { return pricePerHour; }
    public void setPricePerHour(Double pricePerHour) { this.pricePerHour = pricePerHour; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
}