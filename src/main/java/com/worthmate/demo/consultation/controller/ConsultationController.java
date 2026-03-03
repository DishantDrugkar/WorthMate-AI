package com.worthmate.demo.consultation.controller;

import com.worthmate.demo.consultation.entity.ConsultationEntity;
import com.worthmate.demo.consultation.service.ConsultationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultation")
public class ConsultationController {

    private final ConsultationService consultationService;

    public ConsultationController(ConsultationService consultationService) {
        this.consultationService = consultationService;
    }

    // =========================
    // BOOK (STUDENT)
    // =========================

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

    // =========================
    // PAY (STUDENT)
    // =========================

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/pay/{id}")
    public ConsultationEntity pay(
            @PathVariable Long id,
            Authentication authentication) {

        String studentEmail = authentication.getName();

        return consultationService.payForConsultation(
                id,
                studentEmail
        );
    }

    // =========================
    // COMPLETE (MENTOR)
    // =========================

    @PreAuthorize("hasRole('MENTOR')")
    @PostMapping("/complete/{id}")
    public ConsultationEntity complete(
            @PathVariable Long id,
            Authentication authentication) {

        String mentorEmail = authentication.getName();

        return consultationService.completeConsultation(
                id,
                mentorEmail
        );
    }

    // =========================
    // FEEDBACK (STUDENT)
    // =========================

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/feedback/{id}")
    public ConsultationEntity feedback(
            @PathVariable Long id,
            @RequestParam Integer rating,
            Authentication authentication) {

        String studentEmail = authentication.getName();

        return consultationService.giveFeedback(
                id,
                studentEmail,
                rating
        );
    }

    // =========================
    // MENTOR BOOKINGS
    // =========================

    @PreAuthorize("hasRole('MENTOR')")
    @GetMapping("/mentor/{mentorId}")
    public List<ConsultationEntity> mentorBookings(
            @PathVariable Long mentorId) {

        return consultationService.getMentorBookings(mentorId);
    }

    // =========================
    // ADMIN VIEW
    // =========================

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public List<ConsultationEntity> allBookings() {
        return consultationService.getAll();
    }
}