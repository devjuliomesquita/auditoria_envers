package com.juliomesquita.demoauditoria.application.usecases.audit.search.timelineofanentity;

import com.juliomesquita.demoauditoria.application.usecases.commom.SearchQuery;

public record TimelineOfAnEntityInput(Long id, Class<?> entityClass, SearchQuery query) {
}
