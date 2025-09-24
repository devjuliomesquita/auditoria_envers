package com.juliomesquita.demoauditoria.data.livro.entities;

import jakarta.persistence.*;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "livros")
@Audited
@AuditTable(value = "livros_aud", schema = "core_audit")
public class LivroAgg extends BaseEntityWithGeneratedId {

    @Audited(withModifiedFlag = true)
    @Column(name = "titulo", nullable = false)
    private String titulo;

    @OneToOne(mappedBy = "livro", cascade = CascadeType.ALL, orphanRemoval = true)
    private DetalheLivroEnt detalhe;

    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExemplarEnt> exemplares = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "livro_autor_assoc",
        joinColumns = @JoinColumn(name = "livro_id", foreignKey = @ForeignKey(name = "fk_livro_autor_livro")),
        inverseJoinColumns = @JoinColumn(name = "autor_id", foreignKey = @ForeignKey(name = "fk_livro_autor_autor")))
    @AuditJoinTable(name = "livro_autor_assoc_aud", schema = "core_audit")
    private Set<AutorEnt> autores = new HashSet<>();

    public static LivroAgg create(final String titulo) {
        return new LivroAgg(titulo);
    }

    public LivroAgg update(final String titulo, final DetalheLivroEnt detalhe) {
        this.titulo = titulo;
        this.detalhe = detalhe;
        return this;
    }

    public LivroAgg addDetalhe(final DetalheLivroEnt detalhe) {
        this.detalhe = detalhe;
        return this;
    }

    public LivroAgg addExemplar(final ExemplarEnt exemplar) {
        this.exemplares.add(exemplar);
        return this;
    }

    public LivroAgg removeExemplar(final ExemplarEnt exemplar) {
        this.exemplares.remove(exemplar);
        return this;
    }

    public LivroAgg addAutor(final AutorEnt autor) {
        this.autores.add(autor);
        return this;
    }

    public LivroAgg removeAutor(final AutorEnt autor) {
        this.autores.remove(autor);
        return this;
    }

    //    Pojo
    private LivroAgg(final String titulo, final DetalheLivroEnt detalhe, final List<ExemplarEnt> exemplares, final Set<AutorEnt> autores) {
        this.titulo = titulo;
        this.detalhe = detalhe;
        this.exemplares = exemplares;
        this.autores = autores;
    }

    private LivroAgg(final String titulo) {
        this.titulo = titulo;
    }

    protected LivroAgg() {
    }

    public String getTitulo() {
        return titulo;
    }

    public DetalheLivroEnt getDetalhe() {
        return detalhe;
    }

    public List<ExemplarEnt> getExemplares() {
        return exemplares;
    }

    public Set<AutorEnt> getAutores() {
        return autores;
    }
}
