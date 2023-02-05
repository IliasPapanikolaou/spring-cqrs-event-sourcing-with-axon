package com.ipap.paymentservice.command.api.data;

import com.ipap.commonsservice.model.PaymentStatus;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PAYMENT_TBL")
public class Payment {
    @Id
    private String paymentId;
    private String orderId;
    private Date timestamp;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}
