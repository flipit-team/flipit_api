package com.flip.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Charles on 12/06/2021
 */
@Entity
@Getter
@Setter
@Table(name = "countries")
public class Country extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;
}
