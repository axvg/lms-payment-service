package com.lms.payment_service.domain.port;

import com.lms.payment_service.domain.model.Payment;
import java.util.Optional;

public interface PaymentRepository {
    Payment save(Payment payment);
    Optional<Payment> findById(Long id);
}
