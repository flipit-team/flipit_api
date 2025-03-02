package com.flip.data.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author Charles on 21/06/2021
 */
@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order extends BaseEntity {

    @Column(name = "item_id", nullable = false)
    private Long itemId;
}
