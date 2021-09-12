package com.flip.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Charles on 21/06/2021
 */
@Entity
@Getter
@Setter
@Table(name = "biddings")
public class Bidding extends BaseEntity {


    @Override
    public boolean equals(Object object) {
        return (object instanceof Bidding && ((Bidding) object).getId().equals(this.getId()));
    }
}
