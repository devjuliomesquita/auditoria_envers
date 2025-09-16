package com.juliomesquita.demoauditoria.infra.controllers.user;

import com.juliomesquita.demoauditoria.application.usecases.userflow.login.LoginInput;
import com.juliomesquita.demoauditoria.application.usecases.userflow.singin.SingInInput;
import com.juliomesquita.demoauditoria.infra.controllers.shared.DefaultPublicAPIResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "User", description = "API de gerenciamento de usuários.")
public interface UserDoc {
    @Operation(
        operationId = "singIn",
        summary = "Criar um Usuário.",
        description = "Este endpoint recebe os parametros necessarios para a criacao de um usuário.",
        tags = {"User"},
        responses = @ApiResponse(responseCode = "200", description = "Ok")
//        requestBody = @RequestBody(content = {@Content(examples = @ExampleObject(value = ListRolesRequest.exampleRequest))}),
    )
    @DefaultPublicAPIResponses
    @PostMapping("/singIn")
    ResponseEntity<?> singIn(
        @RequestBody SingInInput request
    );

    @Operation(
        operationId = "login",
        summary = "Login de Usuário.",
        description = "Este endpoint recebe os parametros necessarios para o login de um usuário cadastrado.",
        tags = {"User"},
        responses = @ApiResponse(responseCode = "200", description = "Ok")
//        requestBody = @RequestBody(content = {@Content(examples = @ExampleObject(value = ListRolesRequest.exampleRequest))}),
    )
    @DefaultPublicAPIResponses
    @PostMapping("/login")
    ResponseEntity<?> login(
        @RequestBody LoginInput request
    );
}
