package com.juliomesquita.demoAuditoria.data.user.entities;

import com.juliomesquita.demoAuditoria.data.entities.BaseEntityWithGeneratedId;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEnt  extends BaseEntityWithGeneratedId implements UserDetails {

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id", referencedColumnName = "profile_id")
    private ProfileEnt profile;

    @OneToMany(mappedBy = "user")
    private List<TokenEnt> tokens = new ArrayList<>();

    public static UserEnt create(String name, String email, String password, ProfileEnt profile) {
        return new UserEnt(name, email, password, profile);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.profile.getAuthoraties();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

//    Pojo
    private UserEnt(String name, String email, String password, ProfileEnt profile) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.profile = profile;
    }

    protected UserEnt() {
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public ProfileEnt getProfile() {
        return profile;
    }

    public List<TokenEnt> getTokens() {
        return tokens;
    }
}
