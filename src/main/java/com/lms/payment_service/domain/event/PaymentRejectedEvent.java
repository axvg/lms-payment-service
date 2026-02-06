package com.lms.payment_service.domain.event;

import java.math.BigDecimal;

public record PaymentRejectedEvent(
        Long paymentId,
        Long enrollmentId,
        BigDecimal amount,
        String reason
) {
}
