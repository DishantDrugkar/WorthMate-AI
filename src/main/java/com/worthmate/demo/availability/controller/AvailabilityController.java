package com.worthmate.demo.availability.controller;

import com.worthmate.demo.availability.entity.AvailabilitySlotEntity;
import com.worthmate.demo.availability.service.AvailabilityService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/availability")
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    public AvailabilityController(
            AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @PostMapping("/create")
    public AvailabilitySlotEntity createSlot(@RequestParam Long mentorId, @RequestParam String start, @RequestParam String end) {

        return availabilityService.createSlot(
                mentorId,
                LocalDateTime.parse(start),
                LocalDateTime.parse(end)
        );
    }

    @GetMapping("/{mentorId}")
    public List<AvailabilitySlotEntity> getSlots(
            @PathVariable Long mentorId) {

        return availabilityService.getAvailableSlots(mentorId);
    }
}
