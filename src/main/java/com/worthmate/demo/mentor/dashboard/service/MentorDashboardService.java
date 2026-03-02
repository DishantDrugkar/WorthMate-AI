package com.worthmate.demo.mentor.dashboard.service;

import com.worthmate.demo.consultation.entity.ConsultationEntity;
import com.worthmate.demo.consultation.repository.ConsultationRepository;
import com.worthmate.demo.mentor.dashboard.dto.MentorDashboardDTO;
import com.worthmate.demo.mentor.entity.MentorProfileEntity;
import com.worthmate.demo.mentor.repository.MentorRepository;
import com.worthmate.demo.util.ConsultationStatusEnum;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MentorDashboardService {

    private final ConsultationRepository consultationRepository;
    private final MentorRepository mentorRepository;

    public MentorDashboardService(
            ConsultationRepository consultationRepository,
            MentorRepository mentorRepository) {
        this.consultationRepository = consultationRepository;
        this.mentorRepository = mentorRepository;
    }

    public MentorDashboardDTO getDashboard(Long mentorId) {
        // 1️⃣ Get mentor details
        MentorProfileEntity mentor =
                mentorRepository.findById(mentorId)
                        .orElseThrow(() ->
                                new RuntimeException("Mentor not found"));

        // 2️⃣ Total consultations (all statuses)
        Long total =
                consultationRepository.countByMentor_Id(mentorId);

        // 3️⃣ Completed consultations
        Long completed =
                consultationRepository.countByMentor_IdAndStatus(
                        mentorId,
                        ConsultationStatusEnum.COMPLETED);

        // 4️⃣ Upcoming consultations (confirmed only)
        Long upcoming =
                consultationRepository.countByMentor_IdAndStatus(
                        mentorId,
                        ConsultationStatusEnum.CONFIRMED);

        // 5️⃣ List of completed consultations (for earnings & rating)
        List<ConsultationEntity> completedList =
                consultationRepository.findByMentor_IdAndStatus(
                        mentorId,
                        ConsultationStatusEnum.COMPLETED);

        // 6️⃣ Calculate total earnings
        double earnings = completedList.stream()
                .count() * mentor.getPricePerHour();

        // 7️⃣ Calculate average rating
        double avgRating = completedList.stream()
                .filter(c -> c.getRating() != null)
                .mapToInt(ConsultationEntity::getRating)
                .average()
                .orElse(0.0);

        // 8️⃣ Prepare DTO
        MentorDashboardDTO dto = new MentorDashboardDTO();
        dto.setTotalSessions(total);
        dto.setCompletedSessions(completed);
        dto.setUpcomingSessions(upcoming);
        dto.setAverageRating(avgRating);
        dto.setTotalEarnings(earnings);
        dto.setTrustScore(mentor.getTrustScore());

        return dto;
    }
}
