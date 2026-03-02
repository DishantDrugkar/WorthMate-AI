package com.worthmate.demo.consultation.service;

import com.worthmate.demo.availability.entity.AvailabilitySlotEntity;
import com.worthmate.demo.consultation.entity.ConsultationEntity;
import com.worthmate.demo.consultation.repository.ConsultationRepository;
import com.worthmate.demo.mentor.entity.MentorProfileEntity;
import com.worthmate.demo.mentor.repository.MentorRepository;
import com.worthmate.demo.user.entity.UserEntity;
import com.worthmate.demo.user.repository.UserRepository;
import com.worthmate.demo.util.ConsultationStatusEnum;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.worthmate.demo.availability.repository.AvailabilityRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final UserRepository userRepository;
    private final MentorRepository mentorRepository;
    private final AvailabilityRepository availabilityRepository;

    public ConsultationService(
            ConsultationRepository consultationRepository,
            UserRepository userRepository,
            MentorRepository mentorProfileRepository,
            AvailabilityRepository availabilityRepository) {

        this.consultationRepository = consultationRepository;
        this.userRepository = userRepository;
        this.mentorRepository = mentorProfileRepository;
        this.availabilityRepository = availabilityRepository;
    }

    @Transactional
    public ConsultationEntity bookConsultation(
            String studentEmail,
            Long slotId,
            String topic) {

        UserEntity student =
                userRepository.findByEmail(studentEmail);

        AvailabilitySlotEntity slot =
                availabilityRepository.findById(slotId)
                        .orElseThrow(() ->
                                new RuntimeException("Slot not found"));

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

        return consultationRepository.save(consultation);
    }

    @Transactional
    public ConsultationEntity completeConsultation(Long consultationId) {

        ConsultationEntity consultation =
                consultationRepository.findById(consultationId)
                        .orElseThrow(() ->
                                new RuntimeException("Consultation not found"));

        consultation.setStatus(ConsultationStatusEnum.COMPLETED);

        return consultationRepository.save(consultation);
    }

    @Transactional
    public ConsultationEntity giveFeedback(
            Long consultationId,
            Integer rating) {

        if (rating < 1 || rating > 5) {
            throw new RuntimeException("Rating must be between 1 and 5");
        }

        ConsultationEntity consultation =
                consultationRepository.findById(consultationId)
                        .orElseThrow(() ->
                                new RuntimeException("Consultation not found"));

        if (consultation.getStatus() != ConsultationStatusEnum.COMPLETED) {
            throw new RuntimeException("Consultation not completed yet");
        }

        consultation.setRating(rating);
        consultation.setFeedbackGiven(true);

        updateMentorTrustScore(consultation.getMentor());

        return consultationRepository.save(consultation);
    }

    private void updateMentorTrustScore(MentorProfileEntity mentor) {

        List<ConsultationEntity> completedConsultations =
                consultationRepository
                        .findByMentor_Id(mentor.getId())
                        .stream()
                        .filter(c ->
                                c.getStatus() == ConsultationStatusEnum.COMPLETED
                                        && c.getRating() != null)
                        .toList();

        if (completedConsultations.isEmpty()) {
            mentor.setTrustScore(0.0);
        } else {
            double avg = completedConsultations.stream()
                    .mapToInt(ConsultationEntity::getRating)
                    .average()
                    .orElse(0.0);

            mentor.setTrustScore(avg);
        }

        mentorRepository.save(mentor);
    }

    public List<ConsultationEntity> getMentorBookings(Long mentorId) {
        return consultationRepository.findByMentor_Id(mentorId);
    }

    public List<ConsultationEntity> getAll() {
        return consultationRepository.findAll();
    }
}
