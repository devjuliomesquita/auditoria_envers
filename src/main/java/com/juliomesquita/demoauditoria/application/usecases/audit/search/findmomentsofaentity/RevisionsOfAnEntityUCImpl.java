package com.juliomesquita.demoauditoria.application.usecases.audit.search.findmomentsofaentity;

import com.juliomesquita.demoauditoria.application.usecases.audit.search.common.RevisionAuditDtoResponse;
import com.juliomesquita.demoauditoria.application.usecases.commom.Pagination;
import com.juliomesquita.demoauditoria.data.audit.entities.RevisionAuditedEnt;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;

import java.util.List;

public class RevisionsOfAnEntityUCImpl<E> extends RevisionsOfAnEntityUC<E> {

    private EntityManager entityManager;

    public RevisionsOfAnEntityUCImpl(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public RevisionsOfAnEntityOutput execute(final RevisionsOfAnEntityInput<E> input) {
        final AuditReader auditReader = AuditReaderFactory.get(entityManager);

        final List<Object[]> result = auditReader.createQuery()
            .forRevisionsOfEntity(input.entityClass(), false, true)
            .add(AuditEntity.id().eq(input.entityId()))
            .addOrder(AuditEntity.revisionNumber().desc())
            .setFirstResult(input.query().currentPage() * input.query().itemsPerPage())
            .setMaxResults(input.query().itemsPerPage())
            .getResultList();

        if (result.isEmpty()) {
            throw new EntityNotFoundException("No revision found for entity id " + input.entityId());
        }

        final List<RevisionAuditDtoResponse> listResults = result.stream().map(row -> {
            final var revisionData = (RevisionAuditedEnt) row[1];
            final var revisionType = (RevisionType) row[2];
            return new RevisionAuditDtoResponse(revisionData, revisionType.name());
        }).toList();

        final Pagination<RevisionAuditDtoResponse> pagination =
            Pagination.create(listResults, input.query().currentPage(), input.query().itemsPerPage(), listResults.size());

        return new RevisionsOfAnEntityOutput(pagination);
    }
}