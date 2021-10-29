package com.flip.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Charles on 21/06/2021
 */
@Entity
@Getter
@Setter
@Table(name = "items")
public class Item extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image_urls")
    private String imageUrls;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = AuthUser.class)
    @JoinColumn(name = "user", nullable = false)
    private AuthUser owner;
}
