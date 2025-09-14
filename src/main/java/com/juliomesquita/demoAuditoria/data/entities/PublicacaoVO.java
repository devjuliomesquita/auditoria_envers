package com.juliomesquita.demoAuditoria.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.envers.Audited;

@Embeddable
public record PublicacaoVO(
    @Column(name = "editora_nome", length = 250)
    String editora,

    @Column(name = "num_paginas", nullable = false)
    Integer numeroPaginas,

    @Audited(withModifiedFlag = true)
    @Column(name = "ano_publicacao", nullable = false)
    Integer anoPublicacao
) {
    public PublicacaoVO {
        if (editora == null || editora.isBlank()) {
            throw new IllegalArgumentException("Editora não pode ser vazia");
        }
        if (numeroPaginas == null || numeroPaginas <= 0) {
            throw new IllegalArgumentException("Número de páginas deve ser maior que zero");
        }
        if (anoPublicacao == null || anoPublicacao < 1450 || anoPublicacao > 2100) {
            throw new IllegalArgumentException("Ano de publicação inválido");
        }
    }
}
