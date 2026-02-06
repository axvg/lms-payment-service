package com.lms.payment_service.infrastructure.messaging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.payment_service.application.RegisterPaymentUseCase;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentEventConsumer {

    private final ObjectMapper objectMapper;
    private final RegisterPaymentUseCase registerPaymentUseCase;

    public EnrollmentEventConsumer(ObjectMapper objectMapper, RegisterPaymentUseCase registerPaymentUseCase) {
        this.objectMapper = objectMapper;
        this.registerPaymentUseCase = registerPaymentUseCase;
    }

    @KafkaListener(topics = "lms.enrollment.events", groupId = "payment-service")
    public void handleEnrollmentCreated(String message) {
        try {
            JsonNode node = objectMapper.readTree(message);
            if (!node.has("data")) {
                return;
            }
            JsonNode data = node.get("data");
            if (!"EnrollmentCreatedEvent".equals(node.path("metadata").path("eventType").asText())) {
                return;
            }
            Long enrollmentId = data.path("enrollmentId").asLong();
            registerPaymentUseCase.execute(enrollmentId, java.math.BigDecimal.TEN, "APPROVED");
        } catch (Exception e) {
            throw new IllegalStateException("Failed to process enrollment event", e);
        }
    }
}
