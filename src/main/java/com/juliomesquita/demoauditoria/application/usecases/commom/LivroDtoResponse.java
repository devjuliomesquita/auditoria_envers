package com.juliomesquita.demoauditoria.application.usecases.commom;

import com.juliomesquita.demoauditoria.data.livro.entities.*;

import java.util.List;
import java.util.Set;

public record LivroDtoResponse(
    Long id, String titulo, DetalheLivroDtoResponse detalhe, List<ExemplarDtoResponse> exemplares,
    Set<AutorDtoResponse> autores
) {
    public static LivroDtoResponse create(final LivroAgg entity) {
            if (entity == null) {
                return null;
            }
            return new LivroDtoResponse(
                entity.getId(), entity.getTitulo(), DetalheLivroDtoResponse.create(entity.getDetalhe()),
                entity.getExemplares().stream().map(ExemplarDtoResponse::create).toList(),
                entity.getAutores().stream().map(AutorDtoResponse::create).collect(java.util.stream.Collectors.toSet())
            );
    }
}

record DetalheLivroDtoResponse(Long id, IsbnVO isbn, PublicacaoVO publicacao
) {
    public static DetalheLivroDtoResponse create(final DetalheLivroEnt entity) {
            if (entity == null) {
                return null;
            }
            return new DetalheLivroDtoResponse(
                entity.getId(), entity.getIsbn(), entity.getPublicacao()
            );
    }
}

record ExemplarDtoResponse(Long id, String codigoBarras, Boolean disponivel) {
    public static ExemplarDtoResponse create(final ExemplarEnt entity) {
            if (entity == null) {
                return null;
            }
            return new ExemplarDtoResponse(
                entity.getId(), entity.getCodigoBarras(), entity.getDisponivel()
            );
    }

}

//record AutorDtoResponse(Long id, String nome) {
//    public static AutorDtoResponse create(final AutorEnt entity) {
//            if (entity == null) {
//                return null;
//            }
//            return new AutorDtoResponse(
//                entity.getId(), entity.getNome().getValue()
//            );
//    }
//}


