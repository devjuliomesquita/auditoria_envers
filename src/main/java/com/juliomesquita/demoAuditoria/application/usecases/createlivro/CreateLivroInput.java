package com.juliomesquita.demoAuditoria.application.usecases.createlivro;

public record CreateLivroInput(
    String titulo,
    String isbn,
    String editora,
    Integer anoPublicacao,
    Integer numeroPaginas,
    String codigoBarras,
    Boolean disponivel,
    Long idAutor,
    String nomeAutor) {
}
