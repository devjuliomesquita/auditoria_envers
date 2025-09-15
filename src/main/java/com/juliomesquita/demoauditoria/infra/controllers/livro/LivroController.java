package com.juliomesquita.demoauditoria.infra.controllers.livro;

import com.juliomesquita.demoauditoria.application.usecases.livro.createlivro.CreateLivroOutput;
import com.juliomesquita.demoauditoria.application.usecases.livro.createlivro.CreateLivroUC;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping(value = "/livro")
public class LivroController implements LivroDoc {
    private final CreateLivroUC createLivroUC;

    public LivroController(final CreateLivroUC createLivroUC) {
        this.createLivroUC = Objects.requireNonNull(createLivroUC);
    }

    @Override
    public ResponseEntity<?> createLivro(@Valid @RequestBody LivroRequest request) {
        final CreateLivroOutput response = this.createLivroUC.execute(request.input());
        return ResponseEntity.ok(response);
    }
}
