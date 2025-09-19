package com.juliomesquita.demoauditoria.infra.controllers.audit;

import com.juliomesquita.demoauditoria.application.usecases.audit.search.common.AuditEntityType;
import com.juliomesquita.demoauditoria.application.usecases.audit.search.findmomentsofaentity.RevisionsOfAnEntityInput;
import com.juliomesquita.demoauditoria.application.usecases.audit.search.findmomentsofaentity.RevisionsOfAnEntityOutput;
import com.juliomesquita.demoauditoria.application.usecases.audit.search.findmomentsofaentity.RevisionsOfAnEntityUC;
import com.juliomesquita.demoauditoria.application.usecases.audit.search.findmomentsofaentity.RevisionsOfAnEntityUCImpl;
import com.juliomesquita.demoauditoria.application.usecases.audit.search.momentofaentity.MomentOfAnEntityInput;
import com.juliomesquita.demoauditoria.application.usecases.audit.search.momentofaentity.MomentOfAnEntityOutput;
import com.juliomesquita.demoauditoria.application.usecases.audit.search.momentofaentity.MomentOfAnEntityUC;
import com.juliomesquita.demoauditoria.application.usecases.audit.search.momentofaentity.MomentOfAnEntityUCImpl;
import com.juliomesquita.demoauditoria.application.usecases.audit.search.timelineofanentity.TimelineOfAnEntityInput;
import com.juliomesquita.demoauditoria.application.usecases.audit.search.timelineofanentity.TimelineOfAnEntityOutput;
import com.juliomesquita.demoauditoria.application.usecases.audit.search.timelineofanentity.TimelineOfAnEntityUC;
import com.juliomesquita.demoauditoria.application.usecases.audit.search.timelineofanentity.TimelineOfAnEntityUCImpl;
import com.juliomesquita.demoauditoria.application.usecases.commom.AutorDtoResponse;
import com.juliomesquita.demoauditoria.application.usecases.commom.LivroDtoResponse;
import com.juliomesquita.demoauditoria.application.usecases.commom.SearchQuery;
import com.juliomesquita.demoauditoria.data.livro.entities.AutorEnt;
import com.juliomesquita.demoauditoria.data.livro.entities.LivroAgg;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.function.Function;

@RestController
@RequestMapping(value = "/audit")
public class AuditController implements AuditDoc {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public ResponseEntity<?> timelineOfAEntity(
        final Long entityId, final AuditEntityType auditEntityType,
        final Integer currentPage, final Integer itemsPerPage, final String terms, final String sort,
        final String direction
    ) {
        final SearchQuery searchQuery = new SearchQuery(currentPage, itemsPerPage, terms, sort, direction);
        final TimelineOfAnEntityOutput<?> response;

        switch (auditEntityType) {
            case LIVRO -> response = getTimeline(
                entityId,
                LivroAgg.class,
                searchQuery,
                LivroDtoResponse::create
            );
            case AUTOR -> response = getTimeline(
                entityId,
                AutorEnt.class,
                searchQuery,
                AutorDtoResponse::create
            );
            default -> {
                return ResponseEntity.badRequest().body("Tipo de entidade de auditoria não suportado: " + auditEntityType);
            }
        }

        return ResponseEntity.ok(response);
    }

    @Override
    @Transactional
    public ResponseEntity<?> momentOfAEntity(
        final Long momentId, final AuditEntityType auditEntityType
    ) {
        final MomentOfAnEntityOutput<?> response;

        switch (auditEntityType) {
            case LIVRO -> response = getMoment(
                momentId,
                LivroAgg.class,
                LivroDtoResponse::create
            );
            case AUTOR -> response = getMoment(
                momentId,
                AutorEnt.class,
                AutorDtoResponse::create
            );
            default -> {
                return ResponseEntity.badRequest().body("Tipo de entidade de auditoria não suportado: " + auditEntityType);
            }
        }

        return ResponseEntity.ok(response);
    }

    @Override
    @Transactional
    public ResponseEntity<?> findMomentsOfAEntity(
        final Long entityId, final AuditEntityType auditEntityType,
        final Integer currentPage, final Integer itemsPerPage, final String terms, final String sort,
        final String direction
    ) {
        final SearchQuery searchQuery = new SearchQuery(currentPage, itemsPerPage, terms, sort, direction);
        final RevisionsOfAnEntityOutput response;

        switch (auditEntityType) {
            case LIVRO -> response = getRevisions(
                entityId,
                LivroAgg.class,
                searchQuery
            );
            case AUTOR -> response = getRevisions(
                entityId,
                AutorEnt.class,
                searchQuery
            );
            default -> {
                return ResponseEntity.badRequest().body("Tipo de entidade de auditoria não suportado: " + auditEntityType);
            }
        }

        return ResponseEntity.ok(response);
    }

    @Override
    @Transactional
    public ResponseEntity<?> findMomentsOfAEntityByTime(
        final Long entityId, final LocalDate startDate, final LocalDate endDate, final AuditEntityType auditEntityType,
        final Integer currentPage, final Integer itemsPerPage, final String terms, final String sort,
        final String direction
    ) {
        final SearchQuery searchQuery = new SearchQuery(currentPage, itemsPerPage, terms, sort, direction);
        return null;
    }

    private <E, D> TimelineOfAnEntityOutput<D> getTimeline(
        final Long entityId, final Class<E> entityClass, final SearchQuery searchQuery, final Function<E, D> toDto
    ) {
        TimelineOfAnEntityInput<E, D> input = new TimelineOfAnEntityInput<>(entityId, entityClass, searchQuery, toDto);
        TimelineOfAnEntityUC<E, D> useCase = new TimelineOfAnEntityUCImpl<>(entityManager);

        return useCase.execute(input);
    }

    private <E> RevisionsOfAnEntityOutput getRevisions(final Long entityId, final Class<E> entityClass, final SearchQuery searchQuery) {
        final RevisionsOfAnEntityInput<E> input = new RevisionsOfAnEntityInput<>(entityId, entityClass, searchQuery);
        final RevisionsOfAnEntityUC<E> useCase = new RevisionsOfAnEntityUCImpl<>(entityManager);

        return useCase.execute(input);
    }

    private <E, D> MomentOfAnEntityOutput getMoment(final Long revisionId, final Class<E> entityClass, final Function<E, D> toDto) {
        final MomentOfAnEntityInput<E, D> input = new MomentOfAnEntityInput<>(revisionId, entityClass, toDto);
        final MomentOfAnEntityUC<E, D> useCase = new MomentOfAnEntityUCImpl<>(entityManager);

        return useCase.execute(input);
    }
}