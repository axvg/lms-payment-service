package com.lms.payment_service.infrastructure.web.dto;

import java.math.BigDecimal;

public record PaymentEventPayload(
        Long enrollmentId,
        BigDecimal amount,
        String eventType,
        String status
) {
}
