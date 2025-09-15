package com.juliomesquita.demoauditoria.data.user.repositories;

import com.juliomesquita.demoauditoria.data.user.entities.ProfileEnt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEnt, Long> {

    Optional<ProfileEnt> findByName(String name);
}
