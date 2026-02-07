package com.lms.payment_service.infrastructure.persistence.adapter;

import com.lms.payment_service.domain.model.Payment;
import com.lms.payment_service.domain.port.PaymentRepository;
import com.lms.payment_service.infrastructure.persistence.entity.PaymentEntity;
import com.lms.payment_service.infrastructure.persistence.repository.JpaPaymentRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class PaymentRepositoryAdapter implements PaymentRepository {

    private final JpaPaymentRepository repository;

    public PaymentRepositoryAdapter(JpaPaymentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Payment save(Payment payment) {
        PaymentEntity entity = toEntity(payment);
        PaymentEntity saved = repository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Payment> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Payment> findAll() {
        return repository.findAll().stream().map(this::toDomain).toList();
    }

    private PaymentEntity toEntity(Payment payment) {
        PaymentEntity entity = new PaymentEntity();
        entity.setId(payment.id());
        entity.setEnrollmentId(payment.enrollmentId());
        entity.setAmount(payment.amount());
        entity.setStatus(payment.status());
        entity.setProcessedAt(payment.processedAt());
        return entity;
    }

    private Payment toDomain(PaymentEntity entity) {
        return Payment.fromPersistence(
                entity.getId(),
                entity.getEnrollmentId(),
                entity.getAmount(),
                entity.getStatus(),
                entity.getProcessedAt());
    }
}
