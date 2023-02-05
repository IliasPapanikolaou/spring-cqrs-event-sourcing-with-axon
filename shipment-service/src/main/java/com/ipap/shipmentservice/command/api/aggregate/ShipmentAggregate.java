package com.ipap.shipmentservice.command.api.aggregate;

import com.ipap.commonsservice.commands.ShipmentOrderCommand;
import com.ipap.commonsservice.events.OrderShippedEvent;
import com.ipap.commonsservice.model.ShipmentStatus;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class ShipmentAggregate {

    @AggregateIdentifier
    private String shipmentId;
    private String orderId;
    private ShipmentStatus shipmentStatus;

    public ShipmentAggregate() {
    }

    @CommandHandler
    public ShipmentAggregate(ShipmentOrderCommand shipmentOrderCommand) {
        // Validate command
        // Publish order shipped event
        OrderShippedEvent orderShippedEvent = OrderShippedEvent.builder()
                .shipmentId(shipmentOrderCommand.getShipmentId())
                .orderId(shipmentOrderCommand.getOrderId())
                .shipmentStatus(ShipmentStatus.DELIVERED)
                .build();

        AggregateLifecycle.apply(orderShippedEvent);
    }

    @EventSourcingHandler
    public void on(OrderShippedEvent event) {
        this.orderId = event.getOrderId();
        this.shipmentId = event.getShipmentId();
        this.shipmentStatus = event.getShipmentStatus();
    }
}
