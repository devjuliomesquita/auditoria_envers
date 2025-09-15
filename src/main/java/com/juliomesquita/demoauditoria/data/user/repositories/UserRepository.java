package com.juliomesquita.demoauditoria.data.user.repositories;

import com.juliomesquita.demoauditoria.data.user.entities.UserEnt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEnt, UUID> {

    Optional<UserEnt> findByEmail(String email);
}
