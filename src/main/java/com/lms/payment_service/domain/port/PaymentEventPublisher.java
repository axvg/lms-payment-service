package com.lms.payment_service.domain.port;

import com.lms.payment_service.domain.event.EventMetadata;
import com.lms.payment_service.domain.event.PaymentApprovedEvent;
import com.lms.payment_service.domain.event.PaymentRejectedEvent;

public interface PaymentEventPublisher {
    void publishApproved(PaymentApprovedEvent event, EventMetadata metadata);
    void publishRejected(PaymentRejectedEvent event, EventMetadata metadata);
}
