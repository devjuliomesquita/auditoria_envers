package com.juliomesquita.demoauditoria.data.livro.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class NomeAutorVO {
    @Column(name = "nome_autor", nullable = false, length = 250)
    private String value;

    public static NomeAutorVO create(final String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Nome do autor não pode ser vazio");
        }
//        if (!value.matches("^[A-Za-zÀ-ú\\s]+$")) {
//            throw new IllegalArgumentException("Nome do autor contém caracteres inválidos");
//        }
        return new NomeAutorVO(value);
    }

    private NomeAutorVO(String value) {
        this.value = value;
    }

    protected NomeAutorVO() {
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        NomeAutorVO that = (NomeAutorVO) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}

