package com.juliomesquita.demoauditoria.data.livro.entities;

import jakarta.persistence.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "autores")
@Audited
@AuditTable(value = "autores_aud", schema = "core_audit")
public class AutorEnt extends BaseEntityWithGeneratedId {

    @Audited(withModifiedFlag = true)
    @Embedded
    @AttributeOverride(name = "nome", column = @Column(name = "nome_autor"))
    private NomeAutorVO nome;

    @ManyToMany(mappedBy = "autores")
    private Set<LivroAgg> livros = new HashSet<>();

    public static AutorEnt create(final NomeAutorVO nome) {
        return new AutorEnt(nome, new HashSet<>());
    }

    public AutorEnt addLivro(final LivroAgg livro) {
        this.livros.add(livro);
        return this;
    }

    public AutorEnt removeLivro(final LivroAgg livro) {
        this.livros.remove(livro);
        return this;
    }

    public AutorEnt update(final NomeAutorVO nome) {
        this.nome = nome;
        return this;
    }

//    Pojo
    private AutorEnt(NomeAutorVO nome, Set<LivroAgg> livros) {
        this.nome = nome;
        this.livros = livros;
    }

    protected AutorEnt() {
    }

    public NomeAutorVO getNome() {
        return nome;
    }

    public Set<LivroAgg> getLivros() {
        return livros;
    }
}
