package com.juliomesquita.demoauditoria.application.usecases.livro.updatelivro;

public record UpdateLivroInput(
    Long id,
    String titulo,
    String isbn,
    String editora,
    Integer anoPublicacao,
    Integer numeroPaginas,
    String codigoBarras,
    Boolean disponivel
) {
}
