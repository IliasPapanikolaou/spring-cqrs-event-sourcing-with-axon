package com.ipap.orderservice.command.api.saga;

import com.ipap.commonsservice.commands.CompleteOrderCommand;
import com.ipap.commonsservice.commands.ShipmentOrderCommand;
import com.ipap.commonsservice.commands.ValidatePaymentCommand;
import com.ipap.commonsservice.events.OrderCompletedEvent;
import com.ipap.commonsservice.events.OrderShippedEvent;
import com.ipap.commonsservice.events.PaymentProcessedEvent;
import com.ipap.commonsservice.model.OrderStatus;
import com.ipap.commonsservice.model.User;
import com.ipap.commonsservice.queries.GetUserPaymentDetailsQuery;
import com.ipap.orderservice.command.api.events.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Saga
@Slf4j
public class OrderProcessingSaga {

    @Autowired
    private transient CommandGateway commandGateway;
    @Autowired
    private transient QueryGateway queryGateway;

    public OrderProcessingSaga() {
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent event) {
        log.info("OrderCreatedEvent in Saga for Order Id: {}", event.getOrderId());
        // Get Payment Details
        GetUserPaymentDetailsQuery getUserPaymentDetailsQuery = GetUserPaymentDetailsQuery.builder()
                .userId(event.getUserId())
                .build();
        // Create User Object - async -> join when get back the response
        User user;
        try {
            // Get user from user-service
            user = queryGateway.query(getUserPaymentDetailsQuery, ResponseTypes.instanceOf(User.class)).join();
            // Validation
            ValidatePaymentCommand validatePaymentCommand = ValidatePaymentCommand.builder()
                    .paymentId(UUID.randomUUID().toString())
                    .orderId(event.getOrderId())
                    .cardDetails(user.getCardDetails())
                    .build();
            commandGateway.sendAndWait(validatePaymentCommand);
        } catch (Exception e) {
            log.error(e.getMessage());
            // Start the compensating transaction
        }
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(PaymentProcessedEvent event) {
        log.info("PaymentProcessedEvent in Saga for Order Id: {}", event.getOrderId());
        try {
            ShipmentOrderCommand shipmentOrderCommand = ShipmentOrderCommand.builder()
                    .shipmentId(UUID.randomUUID().toString())
                    .orderId(event.getOrderId())
                    .build();
            commandGateway.send(shipmentOrderCommand).join();
        } catch (Exception e) {
            log.error(e.getMessage());
            // Start the compensating transaction
        }
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderShippedEvent event) {
        log.info("OrderShippedEvent in Saga for Order Id: {}", event.getOrderId());
        try {
            CompleteOrderCommand completeCommand = CompleteOrderCommand.builder()
                    .orderId(event.getOrderId())
                    .orderStatus(OrderStatus.APPROVED)
                    .build();
            commandGateway.send(completeCommand);
        } catch (Exception e) {
            log.error(e.getMessage());
            // Start the compensating transaction
        }
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCompletedEvent event) {
        log.info("OrderCompletedEvent in Saga for Order Id: {}", event.getOrderId());
        // We don't create Send Invoice event, so we end the saga flow here
    }
}
