package com.juliomesquita.demoauditoria.application.usecases.audit.search.common;

import com.juliomesquita.demoauditoria.data.audit.entities.RevisionAuditedEnt;

public record AuditReviewOfAnEntityResponse<T>(
    RevisionAuditedEnt revisionAuditedEntity,
    T entity,
    String revisionType
) {
    public static <T> AuditReviewOfAnEntityResponse<T> from(
        final RevisionAuditedEnt revisionAuditedEntity,
        final T entity,
        final String revisionType
    ) {
        return new AuditReviewOfAnEntityResponse<>(revisionAuditedEntity, entity, revisionType);
    }
}
