package com.juliomesquita.demoAuditoria.infra.controllers.livro;

import com.juliomesquita.demoAuditoria.application.usecases.livro.createlivro.CreateLivroInput;

public record LivroRequest(CreateLivroInput input){
}
