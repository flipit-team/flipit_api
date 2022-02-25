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
@Table(name = "addresses")
public class Address extends BaseEntity {

    @Column(name = "line1")
    private String line1;

    @Column(name = "line2")
    private String line2;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "city")
    private String city;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = State.class)
    @JoinColumn(name = "state_fk")
    private State state;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Country.class)
    @JoinColumn(name = "country_fk")
    private Country country;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = AuthUser.class)
    @JoinColumn(name = "app_user_fk", nullable = false)
    private AppUser appUser;
}
