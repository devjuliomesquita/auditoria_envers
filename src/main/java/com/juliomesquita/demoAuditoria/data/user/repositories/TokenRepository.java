package com.juliomesquita.demoAuditoria.data.user.repositories;

import com.juliomesquita.demoAuditoria.data.user.entities.TokenEnt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<TokenEnt, Long> {
    List<TokenEnt> findByUserIdAndExpiredFalseAndRevokedFalse(Long userId);

    Optional<TokenEnt> findByValue(String value);
}
