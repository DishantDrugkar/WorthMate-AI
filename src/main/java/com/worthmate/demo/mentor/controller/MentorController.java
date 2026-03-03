package com.worthmate.demo.mentor.controller;

import com.worthmate.demo.mentor.dto.CreateMentorRequest;
import com.worthmate.demo.mentor.entity.MentorProfileEntity;
import com.worthmate.demo.mentor.service.MentorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mentor")
public class MentorController {

    private final MentorService mentorService;

    public MentorController(MentorService mentorService) {
        this.mentorService = mentorService;
    }

    @PostMapping("/create")
    public MentorProfileEntity createMentor(
            @RequestBody CreateMentorRequest request) {

        return mentorService.createMentor(request);
    }
}