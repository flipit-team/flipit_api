package com.flip.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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
