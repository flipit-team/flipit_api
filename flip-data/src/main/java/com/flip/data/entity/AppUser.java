package com.flip.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flip.data.enums.UserStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Charles on 28/10/2021
 */
@Entity
@Getter
@Setter
@Table(name = "app_users")
public class AppUser extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "bvn")
    private String bvn;

    @Column(name = "dob")
    @Temporal(TemporalType.DATE)
    private Date dOB;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "date_verified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateVerified;

    @Column(name = "is_account_blocked")
    private boolean accountBlocked = false;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = AuthUser.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "auth_user_fk")
    private AuthUser authUser;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Role.class)
    @JoinTable(name = "user_role_mapping",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") })
    private Set<Role> userRoles = new HashSet<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, targetEntity = Address.class)
    @JoinColumn(name="app_user_fk")
    private Set<Address> addresses = new HashSet<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, targetEntity = UserIdentification.class)
    @JoinColumn(name="app_user_fk")
    private Set<UserIdentification> userIdentifications = new HashSet<>();

    public boolean addRole(Role role) {
        return this.userRoles.add(role);
    }

    public boolean removeRole(Role role) {
        return this.userRoles.remove(role);
    }

    @Override
    public boolean equals(Object object) {
        return (object instanceof AppUser && ((AppUser) object).getId().equals(this.getId()));
    }

    public AppUser() { }

    public AppUser(AuthUser authUser) {
        this.setAuthUser(authUser);
    }

    public AppUser(String title, String firstName, String middleName, String lastName, String email, String phoneNumber,
                   String avatar, AuthUser authUser, Set<Role> userRoles, UserStatus status, Date dateVerified,
                   boolean accountBlocked, Set<Address> addresses) {
        this.title = title;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.avatar = avatar;
        this.authUser = authUser;
        this.userRoles = userRoles;
        this.status = status;
        this.dateVerified = dateVerified;
        this.accountBlocked = accountBlocked;
        this.addresses = addresses;
    }
}
