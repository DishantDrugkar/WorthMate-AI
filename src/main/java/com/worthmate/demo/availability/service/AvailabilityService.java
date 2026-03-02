package com.worthmate.demo.availability.service;

import com.worthmate.demo.availability.entity.AvailabilitySlotEntity;
import com.worthmate.demo.availability.repository.AvailabilityRepository;
import com.worthmate.demo.mentor.entity.MentorProfileEntity;
import com.worthmate.demo.mentor.repository.MentorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AvailabilityService {

    private final AvailabilityRepository availabilityRepository;
    private final MentorRepository mentorRepository;

    public AvailabilityService(
            AvailabilityRepository availabilityRepository,
            MentorRepository mentorRepository) {

        this.availabilityRepository = availabilityRepository;
        this.mentorRepository = mentorRepository;
    }

    public AvailabilitySlotEntity createSlot(
            Long mentorId,
            LocalDateTime start,
            LocalDateTime end) {

        MentorProfileEntity mentor =
                mentorRepository.findById(mentorId)
                        .orElseThrow(() ->
                                new RuntimeException("Mentor not found"));

        AvailabilitySlotEntity slot = new AvailabilitySlotEntity();
        slot.setMentor(mentor);
        slot.setStartTime(start);
        slot.setEndTime(end);
        slot.setBooked(false);

        return availabilityRepository.save(slot);
    }

    public List<AvailabilitySlotEntity> getAvailableSlots(Long mentorId) {
        return availabilityRepository
                .findByMentor_IdAndBookedFalse(mentorId);
    }
}