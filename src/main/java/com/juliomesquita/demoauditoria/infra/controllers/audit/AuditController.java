package com.juliomesquita.demoauditoria.infra.controllers.audit;

import com.juliomesquita.demoauditoria.application.usecases.audit.search.common.AuditEntityType;
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

    private <E, D> TimelineOfAnEntityOutput<D> getTimeline(
            Long entityId,
            Class<E> entityClass,
            SearchQuery searchQuery,
            Function<E, D> toDto
    ) {
        TimelineOfAnEntityInput<E, D> input = new TimelineOfAnEntityInput<>(
                entityId,
                entityClass,
                searchQuery,
                toDto
        );

        TimelineOfAnEntityUC<E, D> useCase = new TimelineOfAnEntityUCImpl<>(entityManager);

        return useCase.execute(input);
    }
}