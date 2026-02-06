package com.lms.payment_service.infrastructure.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record RegisterPaymentRequest(
        @NotNull Long enrollmentId,
        @NotNull @DecimalMin(value = "0.01") BigDecimal amount,
        String status
) {
}
