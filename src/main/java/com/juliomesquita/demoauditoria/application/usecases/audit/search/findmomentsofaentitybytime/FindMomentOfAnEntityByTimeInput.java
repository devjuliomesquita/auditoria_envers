package com.juliomesquita.demoauditoria.application.usecases.audit.search.findmomentsofaentitybytime;

import com.juliomesquita.demoauditoria.application.usecases.commom.SearchQuery;

import java.time.LocalDate;
import java.util.function.Function;

public record FindMomentOfAnEntityByTimeInput<E, D>(
        Long entityId,
        LocalDate startDate,
        LocalDate endDate,
        Class<E> entityClass,
        SearchQuery query,
        Function<E, D> toDto
) {
}