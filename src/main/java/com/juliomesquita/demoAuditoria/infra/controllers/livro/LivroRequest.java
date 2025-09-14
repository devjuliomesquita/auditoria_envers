package com.juliomesquita.demoAuditoria.infra.controllers.livro;

import com.juliomesquita.demoAuditoria.application.usecases.createlivro.CreateLivroInput;

public record LivroRequest(CreateLivroInput input){
}
