package com.worthmate.demo.mentor.service;

import com.worthmate.demo.mentor.dto.CreateMentorRequest;
import com.worthmate.demo.mentor.entity.MentorProfileEntity;
import com.worthmate.demo.mentor.repository.MentorRepository;
import com.worthmate.demo.user.entity.UserEntity;
import com.worthmate.demo.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class MentorService {

    private final MentorRepository mentorRepository;
    private final UserRepository userRepository;

    public MentorService(MentorRepository mentorRepository,
                         UserRepository userRepository) {
        this.mentorRepository = mentorRepository;
        this.userRepository = userRepository;
    }

    public MentorProfileEntity createMentor(CreateMentorRequest request) {

        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        MentorProfileEntity mentor = new MentorProfileEntity();
        mentor.setUser(user);
        mentor.setExpertise(request.getExpertise());
        mentor.setPricePerHour(request.getPricePerHour());
        mentor.setTrustScore(0.0);
        mentor.setBio(request.getBio());

        return mentorRepository.save(mentor);
    }
}