package com.juliomesquita.demoauditoria.data.livro.repositories;

import com.juliomesquita.demoauditoria.data.livro.entities.AutorEnt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends JpaRepository<AutorEnt, Long> {
}
