package com.juliomesquita.demoAuditoria.data.user.repositories;

import com.juliomesquita.demoAuditoria.data.user.entities.ProfileEnt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEnt, UUID> {

    Optional<ProfileEnt> findByName(String name);
}
