package com.juliomesquita.demoauditoria.infra.controllers.user;

import com.juliomesquita.demoauditoria.application.usecases.userflow.login.LoginInput;
import com.juliomesquita.demoauditoria.application.usecases.userflow.login.LoginOutput;
import com.juliomesquita.demoauditoria.application.usecases.userflow.login.LoginUC;
import com.juliomesquita.demoauditoria.application.usecases.userflow.singin.SingInInput;
import com.juliomesquita.demoauditoria.application.usecases.userflow.singin.SingInOutput;
import com.juliomesquita.demoauditoria.application.usecases.userflow.singin.SingInUC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController implements UserDoc{
    private final SingInUC singInUC;
    private final LoginUC loginUC;

    public UserController(final SingInUC singInUC, final LoginUC loginUC) {
        this.singInUC = singInUC;
        this.loginUC = loginUC;
    }

    @Override
    public ResponseEntity<?> singIn(@RequestBody SingInInput request) {
        SingInOutput response = this.singInUC.execute(request);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> login(@RequestBody LoginInput request) {
        LoginOutput response = this.loginUC.execute(request);
        return ResponseEntity.ok(response);
    }
}
