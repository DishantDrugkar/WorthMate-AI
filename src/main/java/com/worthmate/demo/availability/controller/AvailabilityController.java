package com.worthmate.demo.availability.controller;

import com.worthmate.demo.availability.dto.CreateSlotRequest;
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
    public AvailabilitySlotEntity createSlot(
            @RequestBody CreateSlotRequest request) {

        return availabilityService.createSlot(
                request.getMentorId(),
                request.getStart(),
                request.getEnd()
        );
    }

    @GetMapping("/{mentorId}")
    public List<AvailabilitySlotEntity> getSlots(
            @PathVariable Long mentorId) {

        return availabilityService.getAvailableSlots(mentorId);
    }
}
