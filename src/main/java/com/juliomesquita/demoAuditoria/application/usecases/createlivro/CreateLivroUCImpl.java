package com.juliomesquita.demoAuditoria.application.usecases.createlivro;

import com.juliomesquita.demoAuditoria.application.usecases.commom.LivroDtoResponse;
import com.juliomesquita.demoAuditoria.data.entities.*;
import com.juliomesquita.demoAuditoria.data.repositories.AutorRepository;
import com.juliomesquita.demoAuditoria.data.repositories.LivroRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CreateLivroUCImpl extends CreateLivroUC {
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    public CreateLivroUCImpl(final LivroRepository livroRepository, final AutorRepository autorRepository) {
        this.livroRepository = Objects.requireNonNull(livroRepository);
        this.autorRepository = Objects.requireNonNull(autorRepository);
    }

    @Transactional
    @Override
    public CreateLivroOutput execute(final CreateLivroInput input) {
        final AutorEnt autor = this.autorRepository.findById(input.idAutor())
            .map(a -> {
                a.update(new NomeAutorVO(input.nomeAutor()));
                return this.autorRepository.save(a);
            })
            .orElseGet(() -> AutorEnt.create(new NomeAutorVO(input.nomeAutor())));

        final var livro = LivroAgg.create(input.titulo());
        final var detalhe = DetalheLivroEnt.create(
            livro, new IsbnVO(input.isbn()), new PublicacaoVO(input.editora(), input.numeroPaginas(), input.anoPublicacao()));
        final var exemplar = ExemplarEnt.create(input.codigoBarras(), input.disponivel(), livro);

        livro
            .addDetalhe(detalhe)
            .addExemplar(exemplar)
            .addAutor(autor);
        LivroAgg livroSaved = this.livroRepository.save(livro);

        return new CreateLivroOutput(LivroDtoResponse.create(livroSaved));
    }
}
