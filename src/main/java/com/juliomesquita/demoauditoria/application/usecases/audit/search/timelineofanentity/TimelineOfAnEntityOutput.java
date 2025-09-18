package com.juliomesquita.demoauditoria.application.usecases.audit.search.timelineofanentity;

import com.juliomesquita.demoauditoria.application.usecases.audit.search.common.AuditReviewOfAnEntityResponse;
import com.juliomesquita.demoauditoria.application.usecases.commom.Pagination;

public record TimelineOfAnEntityOutput<T>(Pagination<AuditReviewOfAnEntityResponse<T>> paginationAuditReviews) {
}