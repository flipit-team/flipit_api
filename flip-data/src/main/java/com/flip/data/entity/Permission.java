package com.flip.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Charles on 20/06/2021
 */
@Entity
@Getter
@Setter
@Table(name = "permissions")
public class Permission extends BaseEntity {
}
