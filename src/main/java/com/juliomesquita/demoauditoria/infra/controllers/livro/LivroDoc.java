package com.juliomesquita.demoauditoria.infra.controllers.livro;

import com.juliomesquita.demoauditoria.infra.controllers.shared.DefaultAuthAPIResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Tag(name = "Livro", description = "API de gerenciamento de livros.")
public interface LivroDoc {
    @Operation(
        operationId = "createLivro",
        summary = "Criar um livro.",
        description = "Este endpoint recebe os parametros necessarios para a criação de um livro.",
        tags = {"Livro"},
        responses = @ApiResponse(responseCode = "201", description = "Created"),
//        requestBody = @RequestBody(content = {@Content(examples = @ExampleObject(value = ListRolesRequest.exampleRequest))}),
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @DefaultAuthAPIResponses
    @PostMapping()
    ResponseEntity<?> createLivro(
        @RequestBody LivroRequest request
    );

    @Operation(
        operationId = "updateLivro",
        summary = "Criar um livro.",
        description = "Este endpoint recebe os parametros necessarios para a atualização de um livro.",
        tags = {"Livro"},
        responses = @ApiResponse(responseCode = "200", description = "Ok"),
//        requestBody = @RequestBody(content = {@Content(examples = @ExampleObject(value = ListRolesRequest.exampleRequest))}),
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @DefaultAuthAPIResponses
    @PutMapping("/{id}")
    ResponseEntity<?> updateLivro(
        @PathVariable(name = "id") Long id,
        @RequestBody LivroRequest request
    );

    @Operation(
        operationId = "deleteLivro",
        summary = "Deletar um livro.",
        description = "Este endpoint recebe os parametros necessarios para a exclusão de um livro.",
        tags = {"Livro"},
        responses = @ApiResponse(responseCode = "204", description = "No Content"),
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @DefaultAuthAPIResponses
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteLivro(@PathVariable(name = "id") Long id);

    @Operation(
        operationId = "populateDataBaseLivro",
        summary = "Criar vários livros automaticamente.",
        description = "Este endpoint cria vários livros automaticamente.",
        tags = {"Livro"},
        responses = @ApiResponse(responseCode = "201", description = "Created"),
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @DefaultAuthAPIResponses
    @PostMapping("populate-database")
    ResponseEntity<?> populateDataBaseLivro();

    @Operation(
        operationId = "updateDataBaseLivro",
        summary = "Atualiza vários livros automaticamente.",
        description = "Este endpoint atualiza vários livros automaticamente.",
        tags = {"Livro"},
        responses = @ApiResponse(responseCode = "200", description = "Ok"),
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @DefaultAuthAPIResponses
    @PostMapping("update-database")
    ResponseEntity<?> updateDataBaseLivro();
}
