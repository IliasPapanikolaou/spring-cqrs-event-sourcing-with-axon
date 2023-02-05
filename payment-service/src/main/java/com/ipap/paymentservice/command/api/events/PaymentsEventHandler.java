package com.ipap.paymentservice.command.api.events;

import com.ipap.commonsservice.events.PaymentCancelledEvent;
import com.ipap.commonsservice.events.PaymentProcessedEvent;
import com.ipap.commonsservice.model.PaymentStatus;
import com.ipap.paymentservice.command.api.data.Payment;
import com.ipap.paymentservice.command.api.data.PaymentRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PaymentsEventHandler {

    private final PaymentRepository paymentRepository;

    public PaymentsEventHandler(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @EventHandler
    public void on(PaymentProcessedEvent event) {
        Payment payment = Payment.builder()
                .paymentId(event.getPaymentId())
                .orderId(event.getOrderId())
                .paymentStatus(PaymentStatus.COMPLETED)
                .timestamp(new Date())
                .build();
        paymentRepository.save(payment);
    }

    @EventHandler
    public void on(PaymentCancelledEvent event) {
        paymentRepository.findById(event.getPaymentId()).ifPresent(payment -> {
            payment.setPaymentStatus(event.getPaymentStatus());
            paymentRepository.save(payment);
        });
    }
}
