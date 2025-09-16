package com.juliomesquita.demoauditoria.application.usecases.commom;

public record SearchQuery(
    int currentPage,
    int itemsPerPage,
    String terms,
    String sort,
    String direction
) {
}
