package com.ipap.commonsservice.commands;

import com.ipap.commonsservice.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class CancelOrderCommand {

    @TargetAggregateIdentifier
    private String orderId;
    private final OrderStatus orderStatus = OrderStatus.CANCELLED;
}
