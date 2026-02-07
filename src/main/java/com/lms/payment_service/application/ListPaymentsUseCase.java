package com.lms.payment_service.application;

import com.lms.payment_service.domain.model.Payment;
import com.lms.payment_service.domain.port.PaymentRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ListPaymentsUseCase {

    private final PaymentRepository repository;

    public ListPaymentsUseCase(PaymentRepository repository) {
        this.repository = repository;
    }

    public List<Payment> listAll() {
        return repository.findAll();
    }
}
