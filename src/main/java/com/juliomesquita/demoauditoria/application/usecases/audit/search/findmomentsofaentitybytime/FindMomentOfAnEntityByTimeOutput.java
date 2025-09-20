package com.juliomesquita.demoauditoria.application.usecases.audit.search.findmomentsofaentitybytime;

import com.juliomesquita.demoauditoria.application.usecases.audit.search.common.AuditReviewOfAnEntityResponse;
import com.juliomesquita.demoauditoria.application.usecases.commom.Pagination;

public record FindMomentOfAnEntityByTimeOutput<T>(Pagination<AuditReviewOfAnEntityResponse<T>> paginationAuditReviews) {
}