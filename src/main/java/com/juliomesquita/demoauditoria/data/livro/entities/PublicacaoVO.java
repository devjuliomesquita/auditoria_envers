package com.juliomesquita.demoauditoria.data.livro.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.envers.Audited;

import java.util.Objects;

@Embeddable
public class PublicacaoVO {
    @Column(name = "editora_nome", length = 250)
    private String editora;

    @Column(name = "num_paginas", nullable = false)
    private Integer numeroPaginas;

    @Audited(withModifiedFlag = true)
    @Column(name = "ano_publicacao", nullable = false)
    private Integer anoPublicacao;

    public static PublicacaoVO create(final String editora, final Integer numeroPaginas, final Integer anoPublicacao) {
        if (editora == null || editora.isBlank()) {
            throw new IllegalArgumentException("Editora não pode ser vazia");
        }
        if (numeroPaginas == null || numeroPaginas <= 0) {
            throw new IllegalArgumentException("Número de páginas deve ser maior que zero");
        }
        if (anoPublicacao == null || anoPublicacao < 1450 || anoPublicacao > 2100) {
            throw new IllegalArgumentException("Ano de publicação inválido");
        }
        return new PublicacaoVO(editora, numeroPaginas, anoPublicacao);
    }

    private PublicacaoVO(final String editora, final Integer numeroPaginas, final Integer anoPublicacao) {
        this.editora = editora;
        this.numeroPaginas = numeroPaginas;
        this.anoPublicacao = anoPublicacao;
    }

    protected PublicacaoVO() {
    }

    public String getEditora() {
        return editora;
    }

    public Integer getNumeroPaginas() {
        return numeroPaginas;
    }

    public Integer getAnoPublicacao() {
        return anoPublicacao;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PublicacaoVO that = (PublicacaoVO) o;
        return Objects.equals(editora, that.editora) && Objects.equals(numeroPaginas, that.numeroPaginas) && Objects.equals(anoPublicacao, that.anoPublicacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(editora, numeroPaginas, anoPublicacao);
    }
}
