package com.flip.entity;

import com.flip.enums.UserStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Charles on 12/06/2021
 */
@Entity
@Getter
@Setter
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email_address", unique = true)
    private String emailAddress;

    @Column(name = "password")
    private String password;

    @Column(name = "reset_password")
    private boolean resetPassword = false;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "avatar")
    private String avatar;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Role.class)
    @JoinTable(name = "user_role_mapping",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") })
    private Set<Role> userRoles = new HashSet<>();

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "date_verified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateVerified;

    @Column(name = "verification_code")
    private String verificationCode;

    @Column(name = "is_verified")
    private boolean verified = false;

    @Column(name = "is_account_blocked")
    private boolean accountBlocked = false;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Address.class)
    @JoinColumn(name="user_fk")
    private Set<Address> addresses = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "created_by")
    private User createdBy;

    public boolean addRole(Role role) {
        return this.userRoles.add(role);
    }

    public boolean removeRole(Role role) {
        return this.userRoles.remove(role);
    }

    @Override
    public boolean equals(Object object) {
        return (object instanceof User && ((User) object).getId().equals(this.getId()));
    }

    public User() { }

    public User(Long id, String title, String firstName, String lastName, String email, String mobile, String password,
                List<Role> roles, Date dateVerified, boolean verified, UserStatus status, User createdBy) {
        this.setId(id);
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = email;
        this.phoneNumber = mobile;
        this.password = password;
        this.userRoles = new HashSet<>(roles);
        this.dateVerified = dateVerified;
        this.verified = verified;
        this.status = status;
        this.createdBy = createdBy;
    }
}
