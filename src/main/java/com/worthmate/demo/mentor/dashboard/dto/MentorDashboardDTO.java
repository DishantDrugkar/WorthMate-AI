package com.worthmate.demo.mentor.dashboard.dto;

public class MentorDashboardDTO {
    private Long totalSessions;
    private Long completedSessions;
    private Long upcomingSessions;
    private Double averageRating;
    private Double totalEarnings;
    private Double trustScore;

    public Long getTotalSessions() {
        return totalSessions;
    }

    public void setTotalSessions(Long totalSessions) {
        this.totalSessions = totalSessions;
    }

    public Long getCompletedSessions() {
        return completedSessions;
    }

    public void setCompletedSessions(Long completedSessions) {
        this.completedSessions = completedSessions;
    }

    public Long getUpcomingSessions() {
        return upcomingSessions;
    }

    public void setUpcomingSessions(Long upcomingSessions) {
        this.upcomingSessions = upcomingSessions;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Double getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(Double totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

    public Double getTrustScore() {
        return trustScore;
    }

    public void setTrustScore(Double trustScore) {
        this.trustScore = trustScore;
    }
}
