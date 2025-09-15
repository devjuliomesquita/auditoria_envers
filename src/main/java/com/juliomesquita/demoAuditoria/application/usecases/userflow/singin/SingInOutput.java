package com.juliomesquita.demoAuditoria.application.usecases.userflow.singin;

import com.juliomesquita.demoAuditoria.application.usecases.commom.AuthenticationDtoResponse;

import java.io.Serializable;

public record SingInOutput(AuthenticationDtoResponse authenticationDtoResponse) {
}
