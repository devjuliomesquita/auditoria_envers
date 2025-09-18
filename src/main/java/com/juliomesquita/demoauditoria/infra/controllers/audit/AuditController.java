package com.juliomesquita.demoauditoria.infra.controllers.audit;

import com.juliomesquita.demoauditoria.application.usecases.audit.search.common.AuditEntityType;
import com.juliomesquita.demoauditoria.application.usecases.audit.search.timelineofanentity.TimelineOfAnEntityInput;
import com.juliomesquita.demoauditoria.application.usecases.audit.search.timelineofanentity.TimelineOfAnEntityOutput;
import com.juliomesquita.demoauditoria.application.usecases.audit.search.timelineofanentity.TimelineOfAnEntityUC;
import com.juliomesquita.demoauditoria.application.usecases.audit.search.timelineofanentity.TimelineOfAnEntityUCImpl;
import com.juliomesquita.demoauditoria.application.usecases.commom.LivroDtoResponse;
import com.juliomesquita.demoauditoria.application.usecases.commom.SearchQuery;
import com.juliomesquita.demoauditoria.data.livro.entities.LivroAgg;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/audit")
public class AuditController implements AuditDoc {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public ResponseEntity<?> timelineOfAEntity(
        final Long entityId, final AuditEntityType auditEntityType,
        final Integer currentPage, final Integer itemsPerPage, final String terms, final String sort,
        final String direction
    ) {
        final SearchQuery searchQuery = new SearchQuery(currentPage, itemsPerPage, terms, sort, direction);

        TimelineOfAnEntityInput<LivroAgg, LivroDtoResponse> input2 = new TimelineOfAnEntityInput<>(entityId, LivroAgg.class, searchQuery, LivroDtoResponse::create);

        TimelineOfAnEntityUC<LivroAgg, LivroDtoResponse> useCase =
            new TimelineOfAnEntityUCImpl<>(entityManager);

        TimelineOfAnEntityOutput<LivroDtoResponse> response = useCase.execute(input2);

        return ResponseEntity.ok(response);
    }
}
