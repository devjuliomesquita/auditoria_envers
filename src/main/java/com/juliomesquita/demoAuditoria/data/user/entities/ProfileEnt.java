package com.juliomesquita.demoAuditoria.data.user.entities;

import com.juliomesquita.demoAuditoria.data.user.enums.PermissionTypes;
import jakarta.persistence.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "tb_profile")
public class ProfileEnt implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "profile_id", nullable = false)
    private UUID id;

    @Column(name = "profile_name")
    private String name;

    @Column(name = "profile_description")
    private String description;

    @OneToMany(mappedBy = "profile")
    private List<UserEnt> users;

    @Enumerated(EnumType.STRING)
    @Column(name = "permissions")
    private Set<PermissionTypes> permissions;

    public List<SimpleGrantedAuthority> getAuthoraties(){
        List<SimpleGrantedAuthority> permissionsList = new ArrayList<>(this.getPermissions()
            .stream()
            .map(p -> new SimpleGrantedAuthority(p.name()))
            .toList());
        permissionsList.add(new SimpleGrantedAuthority("ROLE_" + this.name));
        return permissionsList;
    }

    public Set<PermissionTypes> getPermissions() {
        return permissions;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<UserEnt> getUsers() {
        return users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfileEnt profile = (ProfileEnt) o;
        return Objects.equals(id, profile.id) && Objects.equals(name, profile.name) && Objects.equals(description, profile.description) && Objects.equals(users, profile.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, users);
    }

}
