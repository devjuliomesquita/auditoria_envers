package com.juliomesquita.demoauditoria.infra;

import com.juliomesquita.demoauditoria.application.usecases.audit.search.timelineofanentity.TimelineOfAnEntityInput;
import com.juliomesquita.demoauditoria.application.usecases.audit.search.timelineofanentity.TimelineOfAnEntityUC;
import com.juliomesquita.demoauditoria.application.usecases.commom.SearchQuery;
import com.juliomesquita.demoauditoria.data.livro.entities.LivroAgg;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Initialize implements CommandLineRunner {
    private final TimelineOfAnEntityUC timelineOfAnEntityUC;
    public Initialize(TimelineOfAnEntityUC timelineOfAnEntityUC) {
        this.timelineOfAnEntityUC = timelineOfAnEntityUC;
    }

    @Override
    public void run(String... args) throws Exception {
        timelineOfAnEntityUC.execute(new TimelineOfAnEntityInput(1L, LivroAgg.class, new SearchQuery(1, 10, "", "id", "asc")));

    }
}
