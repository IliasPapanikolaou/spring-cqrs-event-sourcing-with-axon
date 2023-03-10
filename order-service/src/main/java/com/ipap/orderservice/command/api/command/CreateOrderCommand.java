package com.ipap.orderservice.command.api.command;

import com.ipap.commonsservice.model.OrderStatus;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CreateOrderCommand {

    @TargetAggregateIdentifier
    private String orderId;
    private String productId;
    private String userId;
    private String addressId;
    private Integer quantity;
    private OrderStatus orderStatus;
}
