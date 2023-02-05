package com.ipap.commonsservice.events;

import com.ipap.commonsservice.model.PaymentStatus;
import lombok.Data;

@Data
public class PaymentCancelledEvent {

    private String paymentId;
    private String orderId;
    private final PaymentStatus paymentStatus = PaymentStatus.CANCELLED;
}
