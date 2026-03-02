package com.worthmate.demo.feeback.repository;

import com.worthmate.demo.feeback.entity.FeedbackEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<FeedbackEntity, Long> {
    List<FeedbackEntity> findByConsultationMentorId(Long mentorId);
}
