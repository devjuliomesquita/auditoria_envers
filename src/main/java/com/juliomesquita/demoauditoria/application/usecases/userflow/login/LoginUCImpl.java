package com.juliomesquita.demoauditoria.application.usecases.userflow.login;

import com.juliomesquita.demoauditoria.application.usecases.commom.AuthenticationDtoResponse;
import com.juliomesquita.demoauditoria.application.usecases.userflow.singin.SingInOutput;
import com.juliomesquita.demoauditoria.data.user.entities.ProfileEnt;
import com.juliomesquita.demoauditoria.data.user.entities.TokenEnt;
import com.juliomesquita.demoauditoria.data.user.entities.UserEnt;
import com.juliomesquita.demoauditoria.data.user.enums.PermissionTypes;
import com.juliomesquita.demoauditoria.data.user.enums.TokenType;
import com.juliomesquita.demoauditoria.data.user.repositories.ProfileRepository;
import com.juliomesquita.demoauditoria.data.user.repositories.TokenRepository;
import com.juliomesquita.demoauditoria.data.user.repositories.UserRepository;
import com.juliomesquita.demoauditoria.infra.configs.security.authentication.AuthenticationService;
import com.juliomesquita.demoauditoria.infra.configs.security.jwt.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LoginUCImpl extends LoginUC {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final ProfileRepository profileRepository;


    public LoginUCImpl(AuthenticationManager authenticationManager, final UserRepository userRepository, final JwtService jwtService,
                       final TokenRepository tokenRepository, final ProfileRepository profileRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.profileRepository = profileRepository;
    }

    @Transactional
    @Override
    public LoginOutput execute(final LoginInput input) {
        this.authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                input.email(),
                input.password()
            )
        );

        final UserEnt user = this.userRepository.findByEmail(input.email())
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));

        final String accessToken = this.createToken(user);
        final TokenEnt token = TokenEnt.create(accessToken, TokenType.BEARER, user);

        this.revokeTokens(user);
        this.tokenRepository.save(token);
        final String refreshToken = this.jwtService.generateRefreshToken(user);
        return new LoginOutput(new AuthenticationDtoResponse(accessToken, refreshToken));
    }

    private String createToken(final UserEnt user){
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("Id", user.getId());
        extraClaims.put("Tag", "User");
        extraClaims.put("Profile", user.getProfile().getName());
        extraClaims.put("Permissions", user.getProfile().getAuthoraties());

        return this.jwtService.generateAccessToken(extraClaims, user);
    }

    private void revokeTokens(final UserEnt user){
        List<TokenEnt> tokenList = this.tokenRepository.findByUserIdAndExpiredFalseAndRevokedFalse(user.getId());
        if(tokenList.isEmpty()){
            return;
        }
        tokenList.forEach(t -> {
            t.setRevoked(true);
            t.setExpired(true);
        });
        this.tokenRepository.saveAll(tokenList);
    }
}
