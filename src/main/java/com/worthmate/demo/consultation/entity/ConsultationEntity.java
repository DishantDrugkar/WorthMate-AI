package com.worthmate.demo.consultation.entity;

import com.worthmate.demo.mentor.entity.MentorProfileEntity;
import com.worthmate.demo.user.entity.UserEntity;
import com.worthmate.demo.util.ConsultationStatusEnum;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "consultations")
public class ConsultationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserEntity student;

    @ManyToOne
    private MentorProfileEntity mentor;

    private String topic;

    @Enumerated(EnumType.STRING)
    private ConsultationStatusEnum status;

    private LocalDateTime scheduledAt;

    private Boolean feedbackGiven;

    private Integer rating; // 1 to 5

    // Getters & Setters


    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UserEntity getStudent() { return student; }
    public void setStudent(UserEntity student) { this.student = student; }

    public MentorProfileEntity getMentor() { return mentor; }
    public void setMentor(MentorProfileEntity mentor) { this.mentor = mentor; }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    public ConsultationStatusEnum getStatus() { return status; }
    public void setStatus(ConsultationStatusEnum status) { this.status = status; }

    public LocalDateTime getScheduledAt() { return scheduledAt; }
    public void setScheduledAt(LocalDateTime scheduledAt) { this.scheduledAt = scheduledAt; }

    public Boolean getFeedbackGiven() { return feedbackGiven; }
    public void setFeedbackGiven(Boolean feedbackGiven) { this.feedbackGiven = feedbackGiven; }
}
