package com.flip.data.entity;

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
@Table(name = "roles")
public class Role extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "is_system")
    private boolean system = false;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Menu.class)
    @JoinTable(name="role_menu_mapping",
            joinColumns =@JoinColumn(name="role_id", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="menu_id", referencedColumnName="id"))
    private Set<Menu> menus = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Permission.class)
    @JoinColumn(name="role_fk")
    private Set<Permission> permissions = new HashSet<>();
}
