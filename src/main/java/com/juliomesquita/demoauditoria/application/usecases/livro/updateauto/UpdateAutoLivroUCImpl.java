package com.juliomesquita.demoauditoria.application.usecases.livro.updateauto;

import com.github.javafaker.Faker;
import com.juliomesquita.demoauditoria.application.usecases.livro.updatelivro.UpdateLivroInput;
import com.juliomesquita.demoauditoria.application.usecases.livro.updatelivro.UpdateLivroUC;
import com.juliomesquita.demoauditoria.data.livro.entities.LivroAgg;
import com.juliomesquita.demoauditoria.data.livro.repositories.LivroRepository;
import org.springframework.stereotype.Component;

@Component
public class UpdateAutoLivroUCImpl extends UpdateAutoLivroUC {

    private final UpdateLivroUC updateLivroUC;
    private final LivroRepository livroRepository;

    public UpdateAutoLivroUCImpl(UpdateLivroUC updateLivroUC, LivroRepository livroRepository) {
        this.updateLivroUC = updateLivroUC;
        this.livroRepository = livroRepository;
    }


    @Override
    public void execute() {
        final Faker faker = new Faker();

        for (int i = 0; i < 5; i++) {
            final LivroAgg randomLivro = this.livroRepository.findRandomLivro();
            final UpdateLivroInput input = new UpdateLivroInput(
                randomLivro.getId(),
                faker.book().title(),
                faker.code().isbn13(),
                faker.book().publisher(),
                faker.number().numberBetween(1900, 2025),
                faker.number().numberBetween(50, 1500),
                faker.code().ean13(),
                faker.bool().bool()
            );
            this.updateLivroUC.execute(input);
        }
    }
}