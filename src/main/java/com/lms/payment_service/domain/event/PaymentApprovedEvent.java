package com.lms.payment_service.domain.event;

import java.math.BigDecimal;

public record PaymentApprovedEvent(
        Long paymentId,
        Long enrollmentId,
        BigDecimal amount
) {
}
