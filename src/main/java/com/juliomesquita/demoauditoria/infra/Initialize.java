package com.juliomesquita.demoauditoria.infra;

import com.juliomesquita.demoauditoria.application.usecases.audit.search.timelineofanentity.TimelineOfAnEntityInput;
import com.juliomesquita.demoauditoria.application.usecases.audit.search.timelineofanentity.TimelineOfAnEntityOutput;
import com.juliomesquita.demoauditoria.application.usecases.audit.search.timelineofanentity.TimelineOfAnEntityUC;
import com.juliomesquita.demoauditoria.application.usecases.audit.search.timelineofanentity.TimelineOfAnEntityUCImpl;
import com.juliomesquita.demoauditoria.application.usecases.commom.LivroDtoResponse;
import com.juliomesquita.demoauditoria.application.usecases.commom.SearchQuery;
import com.juliomesquita.demoauditoria.data.livro.entities.LivroAgg;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

//@Component
public class Initialize implements CommandLineRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        TimelineOfAnEntityUC<LivroAgg, LivroDtoResponse> useCase =
                new TimelineOfAnEntityUCImpl<>(entityManager);

        SearchQuery searchQuery = new SearchQuery(0, 10, "", "id", "asc");

        TimelineOfAnEntityInput<LivroAgg, LivroDtoResponse> input =
                new TimelineOfAnEntityInput<>(
                        1L,
                        LivroAgg.class,
                        searchQuery,
                        LivroDtoResponse::create
                );

        TimelineOfAnEntityOutput<LivroDtoResponse> result = useCase.execute(input);

        System.out.println("Audit result: " + result);
    }
}