package com.juliomesquita.demoAuditoria.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record IsbnVO(
    @Column(name = "isbn_codigo", nullable = false, length = 20)
    String value
) {

    public IsbnVO {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("ISBN não pode ser vazio");
        }
        if (!value.matches("\\d{3}-\\d{10}")) {
            throw new IllegalArgumentException("ISBN inválido (esperado formato 978-XXXXXXXXXX)");
        }
    }
}
