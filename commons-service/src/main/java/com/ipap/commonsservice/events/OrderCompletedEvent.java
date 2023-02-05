package com.ipap.commonsservice.events;

import com.ipap.commonsservice.model.OrderStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderCompletedEvent {

    private String orderId;
    private OrderStatus orderStatus;
}
