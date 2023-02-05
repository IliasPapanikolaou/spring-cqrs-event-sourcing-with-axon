package com.ipap.commonsservice.commands;

import com.ipap.commonsservice.model.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class CancelPaymentCommand {

    @TargetAggregateIdentifier
    private String paymentId;
    private String orderId;
    private final PaymentStatus paymentStatus = PaymentStatus.CANCELLED;
}
