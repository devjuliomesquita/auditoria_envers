package com.juliomesquita.demoauditoria.application.usecases.livro.deletelivro;

import com.juliomesquita.demoauditoria.data.livro.repositories.LivroRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DeleteLivroUCImpl extends DeleteLivroUC {
    private final LivroRepository livroRepository;

    public DeleteLivroUCImpl(final LivroRepository livroRepository) {
        this.livroRepository = Objects.requireNonNull(livroRepository);
    }

    @Transactional
    @Override
    public Void execute(final DeleteLivroInput input) {
        this.livroRepository.findById(input.id()).ifPresent(this.livroRepository::delete);
        return null;
    }




}
