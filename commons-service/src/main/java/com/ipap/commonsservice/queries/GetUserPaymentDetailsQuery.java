package com.ipap.commonsservice.queries;

import lombok.Builder;

@Builder
public class GetUserPaymentDetailsQuery {
    private String userId;

    public GetUserPaymentDetailsQuery() {
    }

    public GetUserPaymentDetailsQuery(String userId) {
        this.userId = userId;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
