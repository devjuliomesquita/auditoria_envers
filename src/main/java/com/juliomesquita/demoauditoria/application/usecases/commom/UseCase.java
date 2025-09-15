package com.juliomesquita.demoauditoria.application.usecases.commom;

public abstract class UseCase<I, O> {
    public abstract O execute(I input);
}
