package com.juliomesquita.demoauditoria.application.usecases.audit.search.findmomentsofaentitybytime;

import com.juliomesquita.demoauditoria.application.usecases.audit.search.common.AuditReviewOfAnEntityResponse;
import com.juliomesquita.demoauditoria.application.usecases.commom.Pagination;
import com.juliomesquita.demoauditoria.data.audit.entities.RevisionAuditedEnt;
import jakarta.persistence.EntityManager;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class FindMomentOfAnEntityByTimeUCImpl<E, D> extends FindMomentOfAnEntityByTimeUC<E, D> {
    private final EntityManager entityManager;

    public FindMomentOfAnEntityByTimeUCImpl(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public FindMomentOfAnEntityByTimeOutput<D> execute(final FindMomentOfAnEntityByTimeInput<E, D> input) {
        final AuditReader auditReader = AuditReaderFactory.get(entityManager);

        final Date start = java.sql.Timestamp.valueOf(input.startDate().atStartOfDay());
        final Date end = java.sql.Timestamp.valueOf(input.endDate().atTime(LocalTime.MAX));

        final List<Object[]> result = auditReader.createQuery()
            .forRevisionsOfEntity(input.entityClass(), false, true)
            .add(AuditEntity.id().eq(input.entityId()))
            .add(AuditEntity.revisionProperty("revisionData").between(start, end))
            .addOrder(AuditEntity.revisionNumber().desc())
            .setFirstResult(input.query().currentPage() * input.query().itemsPerPage())
            .setMaxResults(input.query().itemsPerPage())
            .getResultList();

        final List<AuditReviewOfAnEntityResponse<D>> listResults = result.stream().map(row -> {
            final E entity = input.entityClass().cast(row[0]);
            final var revisionData = (RevisionAuditedEnt) row[1];
            final var revisionType = (RevisionType) row[2];
            final D dto = input.toDto().apply(entity);
            return AuditReviewOfAnEntityResponse.from(revisionData, dto, revisionType.toString());
        }).toList();

        final Pagination<AuditReviewOfAnEntityResponse<D>> pagination =
            Pagination.create(listResults, input.query().currentPage(), input.query().itemsPerPage(), listResults.size());

        return new FindMomentOfAnEntityByTimeOutput<>(pagination);
    }
}