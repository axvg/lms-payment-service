package com.lms.payment_service.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Payment(
        Long id,
        Long enrollmentId,
        BigDecimal amount,
        Status status,
        LocalDateTime processedAt
) {

    public static Payment create(Long enrollmentId, BigDecimal amount, Status status) {
        return new Payment(null, enrollmentId, amount, status, null);
    }

    public static Payment fromPersistence(
            Long id,
            Long enrollmentId,
            BigDecimal amount,
            Status status,
            LocalDateTime paidAt) {
        return new Payment(id, enrollmentId, amount, status, paidAt);
    }

    public Payment approve() {
        return new Payment(id, enrollmentId, amount, Status.APPROVED, LocalDateTime.now());
    }

    public Payment reject() {
        return new Payment(id, enrollmentId, amount, Status.REJECTED, LocalDateTime.now());
    }

    public enum Status {
        APPROVED,
        REJECTED
    }
}
