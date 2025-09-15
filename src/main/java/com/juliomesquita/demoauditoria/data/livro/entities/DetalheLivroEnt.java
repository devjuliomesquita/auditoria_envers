package com.juliomesquita.demoauditoria.data.livro.entities;

import jakarta.persistence.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;


@Entity
@Table(
    name = "detalhes",
    indexes = {
        @Index(name = "idx_isbn_codigo", columnList = "isbn_codigo", unique = true)
    }
)
@Audited
@AuditTable(value = "detalhes_aud", schema = "core_audit")
public class DetalheLivroEnt extends BaseEntityWithGeneratedId {

    @Audited(withModifiedFlag = true)
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "isbn_codigo"))
    private IsbnVO isbn;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "editora", column = @Column(name = "editora_nome")),
        @AttributeOverride(name = "numeroPaginas", column = @Column(name = "num_paginas")),
        @AttributeOverride(name = "anoPublicacao", column = @Column(name = "ano_publicacao"))
    })
    private PublicacaoVO publicacao;

    @OneToOne
    @JoinColumn(name = "livro_id", foreignKey = @ForeignKey(name = "fk_detalhe_livro"))
    private LivroAgg livro;

    public static DetalheLivroEnt create(final LivroAgg livro, final IsbnVO isbn, final PublicacaoVO publicacao) {
        return new DetalheLivroEnt(isbn, publicacao, livro);
    }

    public DetalheLivroEnt update(final IsbnVO isbn, final PublicacaoVO publicacao) {
        this.isbn = isbn;
        this.publicacao = publicacao;
        return this;
    }

//    Pojo
    private DetalheLivroEnt(final IsbnVO isbn, final PublicacaoVO publicacao, final LivroAgg livro) {
        this.isbn = isbn;
        this.publicacao = publicacao;
        this.livro = livro;
    }

    protected DetalheLivroEnt() {
    }

    public IsbnVO getIsbn() {
        return isbn;
    }

    public PublicacaoVO getPublicacao() {
        return publicacao;
    }

    public LivroAgg getLivro() {
        return livro;
    }
}
