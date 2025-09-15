package com.juliomesquita.demoAuditoria.application.usecases.userflow.singin;

public record SingInInput(String name, String email, String password, String confirmToPassword) {
    public void validatePosswordAndConfirmPassword() {
        if (!this.password.equals(this.confirmToPassword)) {
            throw new IllegalArgumentException("Password e Confimacao do Passaword nao sao iguais.");
        }
    }
}
