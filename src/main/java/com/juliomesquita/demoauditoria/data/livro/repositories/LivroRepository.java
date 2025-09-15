package com.juliomesquita.demoauditoria.data.livro.repositories;

import com.juliomesquita.demoauditoria.data.livro.entities.LivroAgg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<LivroAgg, Long> {
}
