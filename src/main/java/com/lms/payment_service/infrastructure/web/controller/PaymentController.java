package com.lms.payment_service.infrastructure.web.controller;

import com.lms.payment_service.application.GetPaymentUseCase;
import com.lms.payment_service.application.RegisterPaymentUseCase;
import com.lms.payment_service.domain.model.Payment;
import com.lms.payment_service.infrastructure.web.dto.PaymentResponse;
import com.lms.payment_service.infrastructure.web.dto.RegisterPaymentRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final RegisterPaymentUseCase registerPaymentUseCase;
    private final GetPaymentUseCase getPaymentUseCase;

    public PaymentController(RegisterPaymentUseCase registerPaymentUseCase, GetPaymentUseCase getPaymentUseCase) {
        this.registerPaymentUseCase = registerPaymentUseCase;
        this.getPaymentUseCase = getPaymentUseCase;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> create(@Valid @RequestBody RegisterPaymentRequest request) {
        Payment payment = registerPaymentUseCase.execute(request.enrollmentId(), request.amount(), request.status());
        return ResponseEntity.ok(PaymentResponse.from(payment));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> findById(@PathVariable Long id) {
        Payment payment = getPaymentUseCase.findById(id);
        return ResponseEntity.ok(PaymentResponse.from(payment));
    }
}
