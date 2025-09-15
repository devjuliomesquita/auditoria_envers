package com.juliomesquita.demoauditoria.infra.controllers.shared;

import java.util.Set;

public record RestAPIErrorResponse(
    int status,
    String message,
    Set<String> errors
) {
}
