package com.juliomesquita.demoauditoria.application.usecases.audit.search.common;

import com.juliomesquita.demoauditoria.data.livro.entities.AutorEnt;
import com.juliomesquita.demoauditoria.data.livro.entities.LivroAgg;

public enum AuditEntityType {
    LIVRO(LivroAgg.class),
    AUTOR(AutorEnt.class);
    private final Class<?> entityClass;

    AuditEntityType(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }
}
