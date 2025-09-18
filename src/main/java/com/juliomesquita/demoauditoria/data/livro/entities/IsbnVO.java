package com.juliomesquita.demoauditoria.data.livro.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class IsbnVO {
    @Column(name = "isbn_codigo", nullable = false, length = 20)
    private String value;

    public static IsbnVO create(final String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("ISBN não pode ser vazio");
        }
        if (!value.matches("\\d{3}-\\d{10}")) {
            throw new IllegalArgumentException("ISBN inválido (esperado formato 978-XXXXXXXXXX)");
        }
        return new IsbnVO(value);
    }

    private IsbnVO(String value) {
        this.value = value;
    }

    protected IsbnVO() {
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        IsbnVO isbnVO = (IsbnVO) o;
        return Objects.equals(value, isbnVO.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
