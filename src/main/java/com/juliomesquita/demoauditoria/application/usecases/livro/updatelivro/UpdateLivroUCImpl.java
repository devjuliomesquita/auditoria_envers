package com.juliomesquita.demoauditoria.application.usecases.livro.updatelivro;

import com.juliomesquita.demoauditoria.application.usecases.commom.LivroDtoResponse;
import com.juliomesquita.demoauditoria.data.livro.entities.DetalheLivroEnt;
import com.juliomesquita.demoauditoria.data.livro.entities.IsbnVO;
import com.juliomesquita.demoauditoria.data.livro.entities.PublicacaoVO;
import com.juliomesquita.demoauditoria.data.livro.repositories.LivroRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UpdateLivroUCImpl extends UpdateLivroUC {
    private final LivroRepository livroRepository;

    public UpdateLivroUCImpl(final LivroRepository livroRepository) {
        this.livroRepository = Objects.requireNonNull(livroRepository);
    }

    @Transactional
    @Override
    public UpdateLivroOutput execute(final UpdateLivroInput input) {
        final var livroAgg = this.livroRepository.findById(input.id())
            .orElseThrow(() -> new IllegalArgumentException("Livro com id " + input.id() + " não encontrado."));

        IsbnVO isbnVO = IsbnVO.create(input.isbn());
        PublicacaoVO publicacaoVO = PublicacaoVO.create(input.editora(), input.numeroPaginas(), input.anoPublicacao());
        DetalheLivroEnt detalhe = livroAgg.getDetalhe().update(isbnVO, publicacaoVO);
        livroAgg.update(input.titulo(), detalhe);
        livroAgg.getExemplares().forEach(exemplar -> exemplar.update(input.codigoBarras(), input.disponivel()));

        return new UpdateLivroOutput(LivroDtoResponse.create(this.livroRepository.save(livroAgg)));
    }


}
