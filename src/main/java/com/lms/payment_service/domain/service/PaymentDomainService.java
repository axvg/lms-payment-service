package com.lms.payment_service.domain.service;

import com.lms.payment_service.domain.model.Payment;
import java.math.BigDecimal;

public final class PaymentDomainService {

    private PaymentDomainService() {}

    public static Payment evaluate(Payment payment) {
        if (payment.amount().compareTo(BigDecimal.ZERO) <= 0) {
            return payment.reject();
        }
        return payment.approve();
    }
}
