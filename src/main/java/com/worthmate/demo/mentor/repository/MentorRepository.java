package com.worthmate.demo.mentor.repository;

import com.worthmate.demo.mentor.entity.MentorProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentorRepository extends JpaRepository<MentorProfileEntity, Long> {
    MentorProfileEntity findByUserId(Long userId);

}
