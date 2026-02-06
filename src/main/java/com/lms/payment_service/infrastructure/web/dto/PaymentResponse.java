package com.lms.payment_service.infrastructure.web.dto;

import com.lms.payment_service.domain.model.Payment;

public record PaymentResponse(
        Long id,
        Long enrollmentId,
        String amount,
        String status,
        String paidAt
) {

    public static PaymentResponse from(Payment payment) {
        return new PaymentResponse(
                payment.id(),
                payment.enrollmentId(),
                payment.amount().toPlainString(),
                payment.status().name(),
                payment.processedAt() != null ? payment.processedAt().toString() : null
        );
    }
}
