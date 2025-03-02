package com.flip.data.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author Charles on 12/01/2023
 */
@Entity
@Getter
@Setter
@Table(name = "item_categories")
public class ItemCategory extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

}
