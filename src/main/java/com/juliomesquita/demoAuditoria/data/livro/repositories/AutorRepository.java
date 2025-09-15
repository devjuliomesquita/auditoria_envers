package com.juliomesquita.demoAuditoria.data.livro.repositories;

import com.juliomesquita.demoAuditoria.data.livro.entities.AutorEnt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends JpaRepository<AutorEnt, Long> {
}
