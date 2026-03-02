package com.worthmate.demo.mentor.dashboard.controller;

import com.worthmate.demo.mentor.dashboard.dto.MentorDashboardDTO;
import com.worthmate.demo.mentor.dashboard.service.MentorDashboardService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mentor/dashboard")
public class MentorDashboardController {

    private final MentorDashboardService dashboardService;

    public MentorDashboardController(
            MentorDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @PreAuthorize("hasRole('MENTOR')")
    @GetMapping("/{mentorId}")
    public MentorDashboardDTO getDashboard(
            @PathVariable Long mentorId) {

        return dashboardService.getDashboard(mentorId);
    }
}
