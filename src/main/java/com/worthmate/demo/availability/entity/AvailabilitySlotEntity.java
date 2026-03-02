package com.worthmate.demo.availability.entity;

import com.worthmate.demo.mentor.entity.MentorProfileEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "availability_slots")
public class AvailabilitySlotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private MentorProfileEntity mentor;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Boolean booked;

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MentorProfileEntity getMentor() {
        return mentor;
    }

    public void setMentor(MentorProfileEntity mentor) {
        this.mentor = mentor;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Boolean getBooked() {
        return booked;
    }

    public void setBooked(Boolean booked) {
        this.booked = booked;
    }
}
