package com.flip.data.entity;

import com.flip.data.enums.IdentificationType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Charles on 24/02/2022
 */
@Entity
@Getter
@Setter
@Table(name = "user_identification")
public class UserIdentification extends BaseEntity {

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "thumbnail")
    private String thumbnail;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = AppUser.class)
    @JoinColumn(name = "app_user_fk", nullable = false)
    private AppUser appUser;

    @Column(name = "id_type")
    @Enumerated(EnumType.STRING)
    private IdentificationType idType;
}
