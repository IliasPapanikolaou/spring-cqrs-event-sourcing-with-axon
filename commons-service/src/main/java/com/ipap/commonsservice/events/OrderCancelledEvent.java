package com.ipap.commonsservice.events;


import com.ipap.commonsservice.model.OrderStatus;
import lombok.Data;

@Data
public class OrderCancelledEvent {
    private String orderId;
    private final OrderStatus orderStatus = OrderStatus.CANCELLED;
}
