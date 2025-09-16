package com.juliomesquita.demoauditoria.infra.controllers.livro;

import com.juliomesquita.demoauditoria.infra.controllers.shared.DefaultPublicAPIResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "Livro", description = "API de gerenciamento de livros.")
public interface LivroDoc {
    @Operation(
        operationId = "createLivro",
        summary = "Criar um livro.",
        description = "Este endpoint recebe os parametros necessarios para a criacao de um livro.",
        tags = {"Livro"},
        responses = @ApiResponse(responseCode = "200", description = "Ok"),
//        requestBody = @RequestBody(content = {@Content(examples = @ExampleObject(value = ListRolesRequest.exampleRequest))}),
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @DefaultPublicAPIResponses
    @PostMapping()
    ResponseEntity<?> createLivro(
        @RequestBody LivroRequest request
    );
}
