package com.juliomesquita.demoauditoria.data.livro.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record NomeAutorVO(
    @Column(name = "nome_autor", nullable = false, length = 250)
    String value
) {

    public NomeAutorVO {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Nome do autor não pode ser vazio");
        }
        if (!value.matches("^[A-Za-zÀ-ú\\s]+$")) {
            throw new IllegalArgumentException("Nome do autor contém caracteres inválidos");
        }
    }
}

