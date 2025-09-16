package com.juliomesquita.demoauditoria.application.usecases.audit.search.timelineofanentity;

import com.juliomesquita.demoauditoria.application.usecases.commom.LivroDtoResponse;
import com.juliomesquita.demoauditoria.data.audit.entities.RevisionAuditedEnt;
import com.juliomesquita.demoauditoria.data.livro.entities.*;
import com.juliomesquita.demoauditoria.data.livro.repositories.AutorRepository;
import com.juliomesquita.demoauditoria.data.livro.repositories.LivroRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class TimelineOfAnEntityUCImpl extends TimelineOfAnEntityUC {

    @PersistenceContext
    private EntityManager entityManager;

    public TimelineOfAnEntityUCImpl(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public TimelineOfAnEntityOutput execute(final TimelineOfAnEntityInput input) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);

        //Pegar todas as revisões de uma entidade
//        final var revisions = auditReader.getRevisions(input.entityClass(), input.id());
//
//        revisions.forEach(revision -> {
//            final Object[] entityRevision = (Object[]) auditReader.find(input.entityClass(), input.id(), revision);
//            final LivroAgg entity = (LivroAgg) entityRevision[0];
//            final RevisionType revisionType = (RevisionType) entityRevision[2];
//            final RevisionAuditedEnt rev = auditReader.findRevision(RevisionAuditedEnt.class, revision);
//
//        });

//        List<Object[]> result = auditReader.createQuery()
//            .forRevisionsOfEntity(LivroAgg.class, false, true)
//            .add(AuditEntity.id().eq(input.id()))
//            .addOrder(AuditEntity.revisionNumber().desc())
//            .setFirstResult(input.query().currentPage() * input.query().itemsPerPage())
//            .setMaxResults(input.query().itemsPerPage())
//            .getResultList();
//
//        result.forEach(record -> {
//            final var entity = record[0];
//            final var revisionData = (RevisionAuditedEnt) record[1];
//            final var revisionType = record[2];
//            System.out.println("Entity: " + entity);
//            System.out.println("Revision Data: " + revisionData);
//            System.out.println("Revision Type: " + revisionType);
//        });

        return null;
    }
}

/*
final AuditReader auditReader = AuditReaderFactory.get(entityManager);
final List<Number> revisions = auditReader.getRevisions(entityClass, entityId);

final int start = searchQuery.currentPage() * searchQuery.itemsPerPage();
final int end = Math.min(start + searchQuery.itemsPerPage(), revisions.size());
      if (start >= revisions.size()) {
    return null;
    }

final List<Number> paginatedRevisions = revisions.subList(start, end);

final List<AuditReviewOfAnEntityResponse<T>> auditList = paginatedRevisions.stream()
    .map(rev -> {
        final Object[] entityRevision = (Object[]) auditReader.find(entityClass, entityId, rev);
        final T entity = (T) entityRevision[0];
        final RevisionType revisionType = (RevisionType) entityRevision[2];
        final RevisionAuditedEntity revision = auditReader.findRevision(RevisionAuditedEntity.class, rev);
        return new AuditReviewOfAnEntityResponse<T>(revision, entity, revisionType);
    })
    .toList();

      return Pagination.create(auditList, searchQuery.currentPage(), searchQuery.itemsPerPage(), revisions.size());

public record AuditReviewOfAnEntityResponse<T>(
        RevisionAuditedEntity revisionAuditedEntity,
        T entity,
        RevisionType revisionType
) {
}
*/
