package com.juliomesquita.demoauditoria.application.usecases.commom;

import com.juliomesquita.demoauditoria.data.livro.entities.AutorEnt;

public record AutorDtoResponse(Long id, String nome) {
    public static AutorDtoResponse create(AutorEnt autor) {
        // Assumindo que AutorEnt tem getId() e getNome() retorna um objeto que pode ser convertido para String.
        return new AutorDtoResponse(autor.getId(), autor.getNome().toString());
    }
}
