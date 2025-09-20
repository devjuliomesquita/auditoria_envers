package com.juliomesquita.demoauditoria.infra.controllers.livro;

import com.juliomesquita.demoauditoria.application.usecases.livro.createlivro.CreateLivroOutput;
import com.juliomesquita.demoauditoria.application.usecases.livro.createlivro.CreateLivroUC;
import com.juliomesquita.demoauditoria.application.usecases.livro.deletelivro.DeleteLivroInput;
import com.juliomesquita.demoauditoria.application.usecases.livro.deletelivro.DeleteLivroUC;
import com.juliomesquita.demoauditoria.application.usecases.livro.populate.PopulateLivroUC;
import com.juliomesquita.demoauditoria.application.usecases.livro.updateauto.UpdateAutoLivroUC;
import com.juliomesquita.demoauditoria.application.usecases.livro.updatelivro.UpdateLivroInput;
import com.juliomesquita.demoauditoria.application.usecases.livro.updatelivro.UpdateLivroOutput;
import com.juliomesquita.demoauditoria.application.usecases.livro.updatelivro.UpdateLivroUC;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping(value = "/livro")
public class LivroController implements LivroDoc {
    private final CreateLivroUC createLivroUC;
    private final UpdateLivroUC updateLivroUC;
    private final DeleteLivroUC deleteLivroUC;
    private final PopulateLivroUC populateLivroUC;
    private final UpdateAutoLivroUC updateAutoLivroUC;

    public LivroController(
        final CreateLivroUC createLivroUC, final UpdateLivroUC updateLivroUC,
        final DeleteLivroUC deleteLivroUC, final PopulateLivroUC populateLivroUC, UpdateAutoLivroUC updateAutoLivroUC
    ) {
        this.createLivroUC = Objects.requireNonNull(createLivroUC);
        this.updateLivroUC = Objects.requireNonNull(updateLivroUC);
        this.deleteLivroUC = Objects.requireNonNull(deleteLivroUC);
        this.populateLivroUC = Objects.requireNonNull(populateLivroUC);
        this.updateAutoLivroUC = Objects.requireNonNull(updateAutoLivroUC);
    }

    @Override
    public ResponseEntity<?> createLivro(@Valid @RequestBody LivroRequest request) {
        final CreateLivroOutput response = this.createLivroUC.execute(request.input());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> updateLivro(final Long id, @Valid @RequestBody LivroRequest request) {
        final var inputCommand = new UpdateLivroInput(id, request.input().titulo(), request.input().isbn(), request.input().editora(),
            request.input().anoPublicacao(), request.input().numeroPaginas(), request.input().codigoBarras(), request.input().disponivel());
        final UpdateLivroOutput response = this.updateLivroUC.execute(inputCommand);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> deleteLivro(final Long id) {
        this.deleteLivroUC.execute(new DeleteLivroInput(id));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> populateDataBaseLivro() {
        this.populateLivroUC.execute();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> updateDataBaseLivro() {
        this.updateAutoLivroUC.execute();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
