package com.flip.data.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author Charles on 20/06/2021
 */
@Entity
@Getter
@Setter
@Table(name = "permissions")
public class Permission extends BaseEntity {
}
