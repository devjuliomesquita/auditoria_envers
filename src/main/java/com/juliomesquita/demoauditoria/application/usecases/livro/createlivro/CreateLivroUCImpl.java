package com.juliomesquita.demoauditoria.application.usecases.livro.createlivro;

import com.juliomesquita.demoauditoria.application.usecases.commom.LivroDtoResponse;
import com.juliomesquita.demoauditoria.data.livro.entities.*;
import com.juliomesquita.demoauditoria.data.livro.repositories.AutorRepository;
import com.juliomesquita.demoauditoria.data.livro.repositories.LivroRepository;
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
                a.update(NomeAutorVO.create(input.nomeAutor()));
                return this.autorRepository.save(a);
            })
            .orElseGet(() -> AutorEnt.create(NomeAutorVO.create(input.nomeAutor())));

        final var livro = LivroAgg.create(input.titulo());
        final var detalhe = DetalheLivroEnt.create(
            livro, IsbnVO.create(input.isbn()), PublicacaoVO.create(input.editora(), input.numeroPaginas(), input.anoPublicacao()));
        final var exemplar = ExemplarEnt.create(input.codigoBarras(), input.disponivel(), livro);

        livro
            .addDetalhe(detalhe)
            .addExemplar(exemplar)
            .addAutor(autor);
        LivroAgg livroSaved = this.livroRepository.save(livro);

        return new CreateLivroOutput(LivroDtoResponse.create(livroSaved));
    }
}
