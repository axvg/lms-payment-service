package com.lms.payment_service.infrastructure.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.payment_service.domain.event.EventMetadata;
import com.lms.payment_service.domain.event.PaymentApprovedEvent;
import com.lms.payment_service.domain.event.PaymentRejectedEvent;
import com.lms.payment_service.domain.port.PaymentEventPublisher;
import com.lms.payment_service.infrastructure.config.KafkaTopics;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaPaymentEventPublisher implements PaymentEventPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaPaymentEventPublisher(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void publishApproved(PaymentApprovedEvent event, EventMetadata metadata) {
        publish(event.enrollmentId(), new WrappedEvent(event, metadata));
    }

    @Override
    public void publishRejected(PaymentRejectedEvent event, EventMetadata metadata) {
        publish(event.enrollmentId(), new WrappedEvent(event, metadata));
    }

    private void publish(Long enrollmentId, WrappedEvent payload) {
        try {
            String json = objectMapper.writeValueAsString(payload);
            kafkaTemplate.send(KafkaTopics.PAYMENT_EVENTS, enrollmentId.toString(), json);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize payment event", e);
        }
    }

    private record WrappedEvent(Object data, EventMetadata metadata) {}
}
