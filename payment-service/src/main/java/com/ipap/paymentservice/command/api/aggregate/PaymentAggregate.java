package com.ipap.paymentservice.command.api.aggregate;

import com.ipap.commonsservice.commands.ValidatePaymentCommand;
import com.ipap.commonsservice.events.PaymentProcessedEvent;
import com.ipap.commonsservice.model.PaymentStatus;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Slf4j
public class PaymentAggregate {
    @AggregateIdentifier
    private String paymentId;
    private String orderId;
    private PaymentStatus paymentStatus;

    public PaymentAggregate() {
    }

    @CommandHandler
    public PaymentAggregate(ValidatePaymentCommand validatePaymentCommand) {
        // Validate payment details
        // Publish PaymentProcessedEvent
        log.info("Executing ValidatePaymentCommand for OrderId: {} and PaymentId: {}",
                validatePaymentCommand.getOrderId(), validatePaymentCommand.getPaymentId());
        PaymentProcessedEvent paymentProcessedEvent =
                new PaymentProcessedEvent(validatePaymentCommand.getPaymentId(), validatePaymentCommand.getOrderId());
        AggregateLifecycle.apply(paymentProcessedEvent);
        log.info("PaymentProcessedEvent Applied");
    }

    @EventSourcingHandler
    public void on(PaymentProcessedEvent event) {
        this.paymentId = event.getPaymentId();
        this.orderId = event.getOrderId();
    }
}
