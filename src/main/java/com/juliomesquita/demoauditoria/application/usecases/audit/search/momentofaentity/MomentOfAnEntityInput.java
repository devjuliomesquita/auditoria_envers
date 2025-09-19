package com.juliomesquita.demoauditoria.application.usecases.audit.search.momentofaentity;

import java.util.function.Function;

public record MomentOfAnEntityInput<E, D>(
        Long revisionId,
        Class<E> entityClass,
        Function<E, D> toDto
) {
}