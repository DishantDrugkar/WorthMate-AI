package com.worthmate.demo.availability.repository;

import com.worthmate.demo.availability.entity.AvailabilitySlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AvailabilityRepository extends JpaRepository<AvailabilitySlotEntity, Long> {

    List<AvailabilitySlotEntity> findByMentor_IdAndBookedFalse(Long mentorId);
    Optional<AvailabilitySlotEntity> findByMentor_IdAndStartTime(Long mentorId, LocalDateTime startTime);
}
