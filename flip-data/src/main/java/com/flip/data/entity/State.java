package com.flip.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Charles on 12/06/2021
 */
@Entity
@Getter
@Setter
@Table(name = "states")
public class State extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Country.class)
    @JoinColumn(name = "country_fk")
    private Country country;
}
