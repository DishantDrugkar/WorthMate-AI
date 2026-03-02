package com.worthmate.demo.payment.service;

import com.worthmate.demo.availability.entity.AvailabilitySlotEntity;
import com.worthmate.demo.availability.repository.AvailabilityRepository;
import com.worthmate.demo.consultation.entity.ConsultationEntity;
import com.worthmate.demo.consultation.repository.ConsultationRepository;
import com.worthmate.demo.payment.PaymentStatusEnum;
import com.worthmate.demo.payment.entity.PaymentEntity;
import com.worthmate.demo.payment.repository.PaymentRepository;
import com.worthmate.demo.util.ConsultationStatusEnum;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ConsultationRepository consultationRepository;
    private final AvailabilityRepository availabilityRepository;

    public PaymentService(
            PaymentRepository paymentRepository,
            ConsultationRepository consultationRepository,
            AvailabilityRepository availabilityRepository) {
        this.paymentRepository = paymentRepository;
        this.consultationRepository = consultationRepository;
        this.availabilityRepository = availabilityRepository;
    }

    @Transactional
    public PaymentEntity createPayment(Long consultationId) {

        ConsultationEntity consultation =
                consultationRepository.findById(consultationId)
                        .orElseThrow(() ->
                                new RuntimeException("Consultation not found"));

        PaymentEntity payment = new PaymentEntity();
        payment.setConsultation(consultation);
        payment.setAmount(
                consultation.getMentor().getPricePerHour());
        payment.setStatus(PaymentStatusEnum.PENDING);
        payment.setTransactionId(
                "TXN-" + System.currentTimeMillis());

        return paymentRepository.save(payment);
    }

    @Transactional
    public PaymentEntity processPayment(
            Long paymentId,
            boolean success) {

        PaymentEntity payment =
                paymentRepository.findById(paymentId)
                        .orElseThrow(() ->
                                new RuntimeException("Payment not found"));

        if (payment.getStatus() != PaymentStatusEnum.PENDING) {
            throw new RuntimeException("Payment already processed");
        }

        ConsultationEntity consultation = payment.getConsultation();

        if (success) {

            payment.setStatus(PaymentStatusEnum.SUCCESS);
            consultation.setStatus(
                    ConsultationStatusEnum.CONFIRMED);

        } else {

            payment.setStatus(PaymentStatusEnum.FAILED);
            consultation.setStatus(
                    ConsultationStatusEnum.CANCELLED);

            AvailabilitySlotEntity slot =
                    availabilityRepository
                            .findByMentor_IdAndStartTime(
                                    consultation.getMentor().getId(),
                                    consultation.getScheduledAt())
                            .orElseThrow(() ->
                                    new RuntimeException("Slot not found"));

            slot.setBooked(false);
            availabilityRepository.save(slot);
        }

        return paymentRepository.save(payment);
    }
}
