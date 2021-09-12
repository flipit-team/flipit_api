package com.flip.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Charles on 21/06/2021
 */
@Entity
@Getter
@Setter
@Table(name = "transactions")
public class Transaction extends BaseEntity {

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "reference")
    private String reference;

    @Column(name = "transaction_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate = new Date();

    @Override
    public boolean equals(Object object) {
        return (object instanceof Transaction && ((Transaction) object).getId().equals(this.getId()));
    }
}
