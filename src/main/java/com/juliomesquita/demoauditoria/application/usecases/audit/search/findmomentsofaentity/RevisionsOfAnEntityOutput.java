package com.juliomesquita.demoauditoria.application.usecases.audit.search.findmomentsofaentity;

import com.juliomesquita.demoauditoria.application.usecases.audit.search.common.RevisionAuditDtoResponse;
import com.juliomesquita.demoauditoria.application.usecases.commom.Pagination;

public record RevisionsOfAnEntityOutput(Pagination<RevisionAuditDtoResponse> paginationAuditReviews) {
}