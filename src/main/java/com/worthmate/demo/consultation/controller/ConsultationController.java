package com.worthmate.demo.consultation.controller;

import com.worthmate.demo.consultation.entity.ConsultationEntity;
import com.worthmate.demo.consultation.service.ConsultationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/consultation")
public class ConsultationController {

    private final ConsultationService consultationService;

    public ConsultationController(
            ConsultationService consultationService) {
        this.consultationService = consultationService;
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/book")
    public ConsultationEntity book(
            @RequestParam Long slotId,
            @RequestParam String topic,
            Authentication authentication) {

        String studentEmail = authentication.getName();

        return consultationService.bookConsultation(
                studentEmail,
                slotId,
                topic
        );
    }

    @PreAuthorize("hasRole('MENTOR')")
    @PostMapping("/complete/{id}")
    public ConsultationEntity complete(
            @PathVariable Long id) {

        return consultationService.completeConsultation(id);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/feedback/{id}")
    public ConsultationEntity feedback(
            @PathVariable Long id,
            @RequestParam Integer rating) {

        return consultationService.giveFeedback(id, rating);
    }


    @GetMapping("/mentor/{mentorId}")
    public List<ConsultationEntity> mentorBookings(
            @PathVariable Long mentorId) {

        return consultationService.getMentorBookings(mentorId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public List<ConsultationEntity> allBookings() {
        return consultationService.getAll();
    }
}
