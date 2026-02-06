package com.lms.payment_service.application;

import com.lms.payment_service.domain.event.EventMetadata;
import com.lms.payment_service.domain.event.PaymentApprovedEvent;
import com.lms.payment_service.domain.event.PaymentRejectedEvent;
import com.lms.payment_service.domain.exception.PaymentValidationException;
import com.lms.payment_service.domain.model.Payment;
import com.lms.payment_service.domain.port.PaymentEventPublisher;
import com.lms.payment_service.domain.port.PaymentRepository;
import com.lms.payment_service.domain.service.PaymentDomainService;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterPaymentUseCase {

    private final PaymentRepository repository;
    private final PaymentEventPublisher eventPublisher;

    public RegisterPaymentUseCase(PaymentRepository repository, PaymentEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public Payment execute(Long enrollmentId, BigDecimal amount, String status) {
        if (enrollmentId == null || amount == null) {
            throw new PaymentValidationException("Enrollment and amount are required");
        }

        Payment.Status desiredStatus = mapStatus(status);
        Payment payment = Payment.create(enrollmentId, amount, desiredStatus);
        Payment evaluated = PaymentDomainService.evaluate(payment);
        Payment saved = repository.save(evaluated);

        if (saved.status() == Payment.Status.APPROVED) {
            eventPublisher.publishApproved(
                    new PaymentApprovedEvent(saved.id(), saved.enrollmentId(), saved.amount()),
                    EventMetadata.of("PaymentApprovedEvent"));
        } else {
            eventPublisher.publishRejected(
                    new PaymentRejectedEvent(saved.id(), saved.enrollmentId(), saved.amount(), "Auto validation failed"),
                    EventMetadata.of("PaymentRejectedEvent"));
        }
        return saved;
    }

    private Payment.Status mapStatus(String status) {
        if (status == null) {
            return Payment.Status.APPROVED;
        }
        return switch (status.toUpperCase()) {
            case "REJECTED" -> Payment.Status.REJECTED;
            default -> Payment.Status.APPROVED;
        };
    }
}
