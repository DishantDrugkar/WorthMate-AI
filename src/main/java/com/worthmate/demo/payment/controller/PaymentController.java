package com.worthmate.demo.payment.controller;

import com.worthmate.demo.payment.entity.PaymentEntity;
import com.worthmate.demo.payment.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(
            PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create/{consultationId}")
    public PaymentEntity create(
            @PathVariable Long consultationId) {

        return paymentService.createPayment(consultationId);
    }

    @PostMapping("/process/{paymentId}")
    public PaymentEntity process(
            @PathVariable Long paymentId,
            @RequestParam boolean success) {

        return paymentService
                .processPayment(paymentId, success);
    }
}