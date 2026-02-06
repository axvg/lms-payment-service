package com.lms.payment_service.infrastructure.persistence.repository;

import com.lms.payment_service.infrastructure.persistence.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPaymentRepository extends JpaRepository<PaymentEntity, Long> {
}
