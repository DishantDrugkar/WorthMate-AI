package com.worthmate.demo.consultation.service;

import com.worthmate.demo.availability.entity.AvailabilitySlotEntity;
import com.worthmate.demo.availability.repository.AvailabilityRepository;
import com.worthmate.demo.consultation.entity.ConsultationEntity;
import com.worthmate.demo.consultation.repository.ConsultationRepository;
import com.worthmate.demo.mentor.entity.MentorProfileEntity;
import com.worthmate.demo.mentor.repository.MentorRepository;
import com.worthmate.demo.notification.service.EmailService;
import com.worthmate.demo.user.entity.UserEntity;
import com.worthmate.demo.user.repository.UserRepository;
import com.worthmate.demo.util.ConsultationStatusEnum;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final UserRepository userRepository;
    private final MentorRepository mentorRepository;
    private final AvailabilityRepository availabilityRepository;
    private final EmailService emailService;

    public ConsultationService(
            ConsultationRepository consultationRepository,
            UserRepository userRepository,
            MentorRepository mentorRepository,
            AvailabilityRepository availabilityRepository,
            EmailService emailService) {

        this.consultationRepository = consultationRepository;
        this.userRepository = userRepository;
        this.mentorRepository = mentorRepository;
        this.availabilityRepository = availabilityRepository;
        this.emailService = emailService;
    }

    // =========================
    // BOOK CONSULTATION
    // =========================
    @Transactional
    public ConsultationEntity bookConsultation(
            String studentEmail,
            Long slotId,
            String topic) {

        UserEntity student = userRepository.findByEmail(studentEmail);

        AvailabilitySlotEntity slot = availabilityRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        if (slot.getBooked()) {
            throw new RuntimeException("Slot already booked");
        }

        slot.setBooked(true);
        availabilityRepository.save(slot);

        ConsultationEntity consultation = new ConsultationEntity();
        consultation.setStudent(student);
        consultation.setMentor(slot.getMentor());
        consultation.setTopic(topic);
        consultation.setScheduledAt(slot.getStartTime());
        consultation.setStatus(ConsultationStatusEnum.PENDING_PAYMENT);
        consultation.setFeedbackGiven(false);

        ConsultationEntity saved = consultationRepository.save(consultation);

        emailService.sendBookingEmail(
                student.getEmail(),
                student.getName(),
                topic,
                slot.getStartTime().toString()
        );

        return saved;
    }

    // =========================
    // PAY
    // =========================
    @Transactional
    public ConsultationEntity payForConsultation(
            Long consultationId,
            String studentEmail) {

        ConsultationEntity consultation = consultationRepository.findById(consultationId)
                .orElseThrow(() -> new RuntimeException("Consultation not found"));

        if (!consultation.getStudent().getEmail().equals(studentEmail)) {
            throw new RuntimeException("Unauthorized payment");
        }

        if (consultation.getStatus() != ConsultationStatusEnum.PENDING_PAYMENT) {
            throw new RuntimeException("Invalid payment state");
        }

        consultation.setStatus(ConsultationStatusEnum.CONFIRMED);

        ConsultationEntity saved = consultationRepository.save(consultation);

        emailService.sendPaymentEmail(
                consultation.getMentor().getUser().getEmail(),
                consultation.getMentor().getUser().getName(),
                consultation.getScheduledAt().toString()
        );

        return saved;
    }

    // =========================
    // COMPLETE
    // =========================
    @Transactional
    public ConsultationEntity completeConsultation(
            Long consultationId,
            String mentorEmail) {

        ConsultationEntity consultation = consultationRepository.findById(consultationId)
                .orElseThrow(() -> new RuntimeException("Consultation not found"));

        if (!consultation.getMentor().getUser().getEmail().equals(mentorEmail)) {
            throw new RuntimeException("Unauthorized completion");
        }

        if (consultation.getStatus() != ConsultationStatusEnum.CONFIRMED) {
            throw new RuntimeException("Payment not completed yet");
        }

        consultation.setStatus(ConsultationStatusEnum.COMPLETED);

        ConsultationEntity saved = consultationRepository.save(consultation);

        emailService.sendCompletionEmail(
                consultation.getStudent().getEmail(),
                consultation.getStudent().getName()
        );

        return saved;
    }

    // =========================
    // FEEDBACK
    // =========================
    @Transactional
    public ConsultationEntity giveFeedback(
            Long consultationId,
            String studentEmail,
            Integer rating) {

        if (rating < 1 || rating > 5) {
            throw new RuntimeException("Rating must be between 1 and 5");
        }

        ConsultationEntity consultation = consultationRepository.findById(consultationId)
                .orElseThrow(() -> new RuntimeException("Consultation not found"));

        if (!consultation.getStudent().getEmail().equals(studentEmail)) {
            throw new RuntimeException("Unauthorized feedback");
        }

        if (consultation.getStatus() != ConsultationStatusEnum.COMPLETED) {
            throw new RuntimeException("Consultation not completed yet");
        }

        consultation.setRating(rating);
        consultation.setFeedbackGiven(true);

        updateMentorTrustScore(consultation.getMentor());

        ConsultationEntity saved = consultationRepository.save(consultation);

        emailService.sendFeedbackEmail(
                consultation.getMentor().getUser().getEmail(),
                rating.toString()
        );

        return saved;
    }

    // =========================
    // TRUST SCORE UPDATE
    // =========================
    private void updateMentorTrustScore(MentorProfileEntity mentor) {

        List<ConsultationEntity> completedWithRatings =
                consultationRepository.findByMentor_Id(mentor.getId())
                        .stream()
                        .filter(c ->
                                c.getStatus() == ConsultationStatusEnum.COMPLETED
                                        && c.getRating() != null)
                        .toList();

        double avg = completedWithRatings.stream()
                .mapToInt(ConsultationEntity::getRating)
                .average()
                .orElse(0.0);

        mentor.setTrustScore(avg);

        mentorRepository.save(mentor);
    }

    public List<ConsultationEntity> getMentorBookings(Long mentorId) {
        return consultationRepository.findByMentor_Id(mentorId);
    }

    public List<ConsultationEntity> getAll() {
        return consultationRepository.findAll();
    }
}