package com.juliomesquita.demoauditoria.infra.controllers.user;

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

    public UserController(SingInUC singInUC) {
        this.singInUC = singInUC;
    }

    @Override
    public ResponseEntity<?> resgisterUser(@RequestBody SingInInput request) {
        SingInOutput response = this.singInUC.execute(request);
        return ResponseEntity.ok(response);
    }
}
