package com.juliomesquita.demoauditoria.infra.controllers.livro;

import com.juliomesquita.demoauditoria.application.usecases.livro.createlivro.CreateLivroInput;

public record LivroRequest(CreateLivroInput input){
}
