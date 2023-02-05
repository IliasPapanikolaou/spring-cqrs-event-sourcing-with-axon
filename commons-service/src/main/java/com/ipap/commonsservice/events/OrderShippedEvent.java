package com.ipap.commonsservice.events;

import com.ipap.commonsservice.model.ShipmentStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderShippedEvent {

    private String shipmentId;
    private String orderId;
    private ShipmentStatus shipmentStatus;
}
