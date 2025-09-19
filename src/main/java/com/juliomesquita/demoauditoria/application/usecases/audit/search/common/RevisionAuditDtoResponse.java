package com.juliomesquita.demoauditoria.application.usecases.audit.search.common;

import com.juliomesquita.demoauditoria.data.audit.entities.RevisionAuditedEnt;

public record RevisionAuditDtoResponse(
    RevisionAuditedEnt revisionAudit,
    String revisionType
) {
}
