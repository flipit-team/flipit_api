package com.flip.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Charles on 12/06/2021
 */
@Entity
@Getter
@Setter
@Table(name = "countries")
public class Country extends BaseEntity {

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "phone_code")
    private String phone_code;

    @Column(name = "alpha_2_code")
    private String alpha2code;

    @Column(name = "alpha_3_code", unique = true)
    private String alpha3code;

    @Column(name = "number_code", unique = true)
    private String numberCode;

    @Column(name = "enabled")
    private boolean enabled = false;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, targetEntity = State.class)
    @JoinColumn(name="country_fk")
    private Set<State> states = new HashSet<>();
}
