package com.flip.data.entity;

import com.flip.data.enums.CodeType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Charles on 24/02/2022
 */
@Entity
@Getter
@Setter
@Table(name = "auth_code")
public class AuthCode extends BaseEntity {

    @Column(name = "code")
    private String code;

    @Column(name = "used")
    private boolean used = false;

    @Column(name = "expiry_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiryDate;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = AppUser.class)
    @JoinColumn(name = "app_user_fk", nullable = false)
    private AppUser appUser;

    @Column(name = "code_type")
    @Enumerated(EnumType.STRING)
    private CodeType codeType;
}
