package com.ipap.shipmentservice.command.api.data;

import com.ipap.commonsservice.model.ShipmentStatus;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "SHIPMENT_TBL")
public class Shipment {
    @Id
    private String shipmentId;
    private String orderId;
    @Enumerated(EnumType.STRING)
    private ShipmentStatus shipmentStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Shipment shipment = (Shipment) o;
        return shipmentId != null && Objects.equals(shipmentId, shipment.shipmentId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
