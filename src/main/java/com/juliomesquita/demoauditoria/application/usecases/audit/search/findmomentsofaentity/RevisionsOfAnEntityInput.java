package com.juliomesquita.demoauditoria.application.usecases.audit.search.findmomentsofaentity;

import com.juliomesquita.demoauditoria.application.usecases.commom.SearchQuery;

public record RevisionsOfAnEntityInput<E>(
        Long entityId,
        Class<E> entityClass,
        SearchQuery query
) {
}