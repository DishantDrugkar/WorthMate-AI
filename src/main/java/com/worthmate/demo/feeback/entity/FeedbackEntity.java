package com.worthmate.demo.feeback.entity;

import com.worthmate.demo.consultation.entity.ConsultationEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "feedback")
public class FeedbackEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private ConsultationEntity consultation;

    private Integer rating;

    private String comments;

    // Getters & Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ConsultationEntity getConsultation() { return consultation; }
    public void setConsultation(ConsultationEntity consultation) { this.consultation = consultation; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }
}
