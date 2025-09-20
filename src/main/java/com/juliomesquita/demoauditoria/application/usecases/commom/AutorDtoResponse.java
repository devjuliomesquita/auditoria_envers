package com.juliomesquita.demoauditoria.application.usecases.commom;

import com.juliomesquita.demoauditoria.data.livro.entities.AutorEnt;

public record AutorDtoResponse(Long id, String nome) {
    public static AutorDtoResponse create(AutorEnt autor) {
        return new AutorDtoResponse(autor.getId(), autor.getNome().getValue());
    }
}
