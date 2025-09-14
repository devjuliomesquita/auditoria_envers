package com.juliomesquita.demoAuditoria.application.usecases.commom;

public abstract class UseCase<IN, OUT> {
    public abstract OUT execute(IN input);
}
