package com.juliomesquita.demoAuditoria.data.livro.repositories;

import com.juliomesquita.demoAuditoria.data.livro.entities.LivroAgg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<LivroAgg, Long> {
}
