package com.juliomesquita.demoauditoria.application.usecases.audit.search.momentofaentity;

import com.juliomesquita.demoauditoria.application.usecases.audit.search.common.AuditReviewOfAnEntityResponse;
import com.juliomesquita.demoauditoria.data.audit.entities.RevisionAuditedEnt;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;

import java.util.List;

public class MomentOfAnEntityUCImpl<E, D> extends MomentOfAnEntityUC<E, D> {

    private final EntityManager entityManager;

    public MomentOfAnEntityUCImpl(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public MomentOfAnEntityOutput<D> execute(final MomentOfAnEntityInput<E, D> input) {
        final AuditReader auditReader = AuditReaderFactory.get(entityManager);

        final List<Object[]> result = auditReader.createQuery()
            .forRevisionsOfEntity(input.entityClass(), false, true)
            .add(AuditEntity.revisionNumber().eq(input.revisionId()))
            .getResultList();

        if (result.isEmpty()) {
            throw new EntityNotFoundException("No entity found for revision " + input.revisionId());
        }

        final List<AuditReviewOfAnEntityResponse<D>> listResults = result.stream().map(row -> {
            final E entity = input.entityClass().cast(row[0]);
            final var revisionData = (RevisionAuditedEnt) row[1];
            final var revisionType = (RevisionType) row[2];
            final D dto = input.toDto().apply(entity);
            return AuditReviewOfAnEntityResponse.from(revisionData, dto, revisionType.toString());
        }).toList();

        final AuditReviewOfAnEntityResponse<D> entityResult = listResults.get(0);

        return new MomentOfAnEntityOutput<>(entityResult);
    }
}