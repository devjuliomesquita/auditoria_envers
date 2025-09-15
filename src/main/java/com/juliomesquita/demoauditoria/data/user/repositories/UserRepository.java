package com.juliomesquita.demoauditoria.data.user.repositories;

import com.juliomesquita.demoauditoria.data.user.entities.UserEnt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEnt, Long> {

    Optional<UserEnt> findByEmail(String email);
}
