package com.ipap.userservice.query.api.controller;

import com.ipap.commonsservice.model.User;
import com.ipap.commonsservice.queries.GetUserPaymentDetailsQuery;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final QueryGateway queryGateway;

    @GetMapping("{userId}")
    public ResponseEntity<User> getUserPaymentDetails(@PathVariable String userId) {
        GetUserPaymentDetailsQuery getUserPaymentDetailsQuery = GetUserPaymentDetailsQuery.builder()
                .userId(userId)
                .build();
        User user = queryGateway.query(getUserPaymentDetailsQuery, ResponseTypes.instanceOf(User.class)).join();
        return ResponseEntity.ok(user);
    }
}
