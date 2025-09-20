package com.juliomesquita.demoauditoria.application.usecases.livro.populate;

import com.github.javafaker.Faker;
import com.juliomesquita.demoauditoria.application.usecases.livro.createlivro.CreateLivroInput;
import com.juliomesquita.demoauditoria.application.usecases.livro.createlivro.CreateLivroUC;
import com.juliomesquita.demoauditoria.data.livro.repositories.LivroRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PopulateLivroUCImpl extends PopulateLivroUC {
    private final CreateLivroUC createLivroUC;

    public PopulateLivroUCImpl(final CreateLivroUC createLivroUC) {
        this.createLivroUC = Objects.requireNonNull(createLivroUC);
    }

    @Transactional
    @Override
    public void execute() {
        final Faker faker = new Faker();

        for (int i = 0; i < 5; i++) {
            final CreateLivroInput input = new CreateLivroInput(
                faker.book().title(),                         // título
                faker.code().isbn13(),                        // ISBN
                faker.book().publisher(),                     // editora
                faker.number().numberBetween(1900, 2025),     // ano de publicação
                faker.number().numberBetween(50, 1500),       // número de páginas
                faker.code().ean13(),                         // código de barras
                faker.bool().bool(),                          // disponível ou não
                faker.number().randomNumber(5, true),         // idAutor fake
                faker.book().author()                      // nome do autor
            );
            this.createLivroUC.execute(input);
        }
    }


}
