package com.lms.payment_service.application;

import com.lms.payment_service.domain.exception.PaymentNotFoundException;
import com.lms.payment_service.domain.model.Payment;
import com.lms.payment_service.domain.port.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class GetPaymentUseCase {

    private final PaymentRepository repository;

    public GetPaymentUseCase(PaymentRepository repository) {
        this.repository = repository;
    }

    public Payment findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException(id));
    }
}
