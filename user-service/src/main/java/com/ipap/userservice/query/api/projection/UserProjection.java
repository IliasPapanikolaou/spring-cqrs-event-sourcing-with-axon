package com.ipap.userservice.query.api.projection;

import com.ipap.commonsservice.model.CardDetails;
import com.ipap.commonsservice.model.User;
import com.ipap.commonsservice.queries.GetUserPaymentDetailsQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class UserProjection {

    @QueryHandler
    public User getUserPaymentDetails(GetUserPaymentDetailsQuery query) {
        // TODO: Improve with database users instead of mocked user
        CardDetails cardDetails = CardDetails.builder()
                .name("Mary Shoppings")
                .validUntilMonth(12)
                .validUntilYear(2025)
                .cardNumber("123456789")
                .cvv(111)
                .build();

        return User.builder()
                .userId(query.getUserId())
                .firstName("Mary")
                .lastName("Shoppings")
                .cardDetails(cardDetails)
                .build();
    }
}
