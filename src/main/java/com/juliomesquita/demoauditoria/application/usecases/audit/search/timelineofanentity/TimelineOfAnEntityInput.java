package com.juliomesquita.demoauditoria.application.usecases.audit.search.timelineofanentity;

import com.juliomesquita.demoauditoria.application.usecases.commom.SearchQuery;

import java.util.function.Function;

public record TimelineOfAnEntityInput<E, D>(
        Long entityId,
        Class<E> entityClass,
        SearchQuery query,
        Function<E, D> toDto
) {
}