package com.juliomesquita.demoauditoria.application.usecases.audit.search.momentofaentity;

import com.juliomesquita.demoauditoria.application.usecases.audit.search.common.AuditReviewOfAnEntityResponse;

public record MomentOfAnEntityOutput<T>(AuditReviewOfAnEntityResponse<T> auditReviews) {
}