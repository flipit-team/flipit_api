package com.flip.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

/**
 * @author Charles on 12/06/2021
 */
@Entity
@Getter
@Setter
@Table(name = "auth_users")
public class AuthUser implements UserDetails, CredentialsContainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated = new Date();

    @Column(name = "date_updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpdated;

    @Column(nullable = false)
    private String oauthId;

    @Column(name = "reset_password")
    private boolean resetPassword = false;

    @Column(name = "password")
    private String password;

    @Column(name = "username")
	private String username;

    @Column(name = "enabled")
    private boolean enabled = true;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = AuthUser.class)
    @JoinColumn(name = "created_by")
    private AuthUser createdBy;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, targetEntity = Address.class)
    @JoinColumn(name="auth_user_fk")
    private Set<AppUser> appUsers = new HashSet<>();

    @Override
    public boolean equals(Object object) {
        return (object instanceof AuthUser && ((AuthUser) object).getId().equals(this.getId()));
    }

    public AuthUser() {}

    public AuthUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AuthUser(String username, String password, Long id, Date dateDeleted, Date dateCreated, Date dateUpdated,
                    boolean resetPassword, AuthUser createdBy, Set<AppUser> appUsers) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.resetPassword = resetPassword;
        this.createdBy = createdBy;
        this.appUsers = appUsers;
    }

    public void eraseCredentials() {
        this.password = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptySet();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }
}
