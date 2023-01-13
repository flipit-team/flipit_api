package com.flip.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @Column(name = "flip_for_image_urls")
    private String flipForImgUrls;

    @Column(name = "accept_cash")
    private Boolean acceptCash;

    @Column(name = "cash_amount")
    private Double cashAmount;

    @Column(name = "published")
    private boolean published = false;

    @Column(name = "location")
    private String location;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = AppUser.class)
    @JoinColumn(name = "app_user_fk", nullable = false)
    private AppUser appUser;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = ItemCategory.class)
    @JoinTable(name = "item_category_mapping",
            joinColumns = { @JoinColumn(name = "item_id") },
            inverseJoinColumns = { @JoinColumn(name = "category_id") })
    private Set<ItemCategory> itemCategories = new HashSet<>();

}
