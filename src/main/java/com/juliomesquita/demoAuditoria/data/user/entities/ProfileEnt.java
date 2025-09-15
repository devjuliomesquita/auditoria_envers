package com.juliomesquita.demoAuditoria.data.user.entities;

import com.juliomesquita.demoAuditoria.data.entities.BaseEntityWithGeneratedId;
import com.juliomesquita.demoAuditoria.data.user.enums.PermissionTypes;
import jakarta.persistence.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "profiles")
public class ProfileEnt extends BaseEntityWithGeneratedId implements Serializable {

    @Column(name = "profile_name")
    private String name;

    @Column(name = "profile_description")
    private String description;

    @OneToMany(mappedBy = "profile")
    private Set<UserEnt> users = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "permissions")
    private Set<PermissionTypes> permissions = new HashSet<>();

    public static ProfileEnt create(final String name, final String description) {
        return new ProfileEnt(name, description);
    }

    public ProfileEnt update(final String name, final String description) {
        this.name = name;
        this.description = description;
        return this;
    }

    public ProfileEnt addPermission(final PermissionTypes permission) {
        this.permissions.add(permission);
        return this;
    }

    public ProfileEnt addUser(final UserEnt user) {
        this.users.add(user);
        return this;
    }

    public ProfileEnt removeUser(final UserEnt user) {
        this.users.remove(user);
        return this;
    }

    public ProfileEnt removePermission(final PermissionTypes permission) {
        this.permissions.remove(permission);
        return this;
    }

    public List<SimpleGrantedAuthority> getAuthoraties(){
        List<SimpleGrantedAuthority> permissionsList = new ArrayList<>(this.getPermissions()
            .stream()
            .map(p -> new SimpleGrantedAuthority(p.name()))
            .toList());
        permissionsList.add(new SimpleGrantedAuthority("ROLE_" + this.name));
        return permissionsList;
    }

    private ProfileEnt(String name, String description) {
        this.name = name;
        this.description = description;
    }

    protected ProfileEnt() {
    }

    public Set<PermissionTypes> getPermissions() {
        return permissions;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Set<UserEnt> getUsers() {
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
