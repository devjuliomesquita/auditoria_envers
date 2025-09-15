package com.juliomesquita.demoauditoria.data.livro.entities;

import jakarta.persistence.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

@Entity
@Table(
    name = "exemplares",
    indexes = {
        @Index(name = "idx_codigo_barras", columnList = "codigo_barras", unique = true)
    }
)
@Audited
@AuditTable(value = "exemplares_aud", schema = "core_audit")
public class ExemplarEnt extends BaseEntityWithGeneratedId {

    @Column(name = "codigo_barras")
    private String codigoBarras;

    @NotAudited
    @Column(name = "isDisponivel")
    private Boolean disponivel;

    @ManyToOne
    @JoinColumn(name = "livro_id", foreignKey = @ForeignKey(name = "fk_exemplar_livro"))
    private LivroAgg livro;

    public static ExemplarEnt create(final String codigoBarras, final Boolean disponivel, final LivroAgg livro) {
        return new ExemplarEnt(codigoBarras, disponivel, livro);
    }

    public ExemplarEnt update(final String codigoBarras, final Boolean disponivel) {
        this.codigoBarras = codigoBarras;
        this.disponivel = disponivel;
        return this;
    }

//    Pojo
    private ExemplarEnt(final String codigoBarras, final Boolean disponivel, final LivroAgg livro) {
        this.codigoBarras = codigoBarras;
        this.disponivel = disponivel;
        this.livro = livro;
    }

    protected ExemplarEnt() {
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public Boolean getDisponivel() {
        return disponivel;
    }

    public LivroAgg getLivro() {
        return livro;
    }
}
