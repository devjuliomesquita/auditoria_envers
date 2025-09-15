package com.juliomesquita.demoAuditoria.application.usecases.userflow.singin;

import com.juliomesquita.demoAuditoria.application.usecases.commom.AuthenticationDtoResponse;
import com.juliomesquita.demoAuditoria.data.user.entities.ProfileEnt;
import com.juliomesquita.demoAuditoria.data.user.entities.TokenEnt;
import com.juliomesquita.demoAuditoria.data.user.entities.UserEnt;
import com.juliomesquita.demoAuditoria.data.user.enums.PermissionTypes;
import com.juliomesquita.demoAuditoria.data.user.enums.TokenType;
import com.juliomesquita.demoAuditoria.data.user.repositories.ProfileRepository;
import com.juliomesquita.demoAuditoria.data.user.repositories.TokenRepository;
import com.juliomesquita.demoAuditoria.data.user.repositories.UserRepository;
import com.juliomesquita.demoAuditoria.infra.configs.security.jwt.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SingInUCImpl extends SingInUC{
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final ProfileRepository profileRepository;


    public SingInUCImpl(PasswordEncoder passwordEncoder, final UserRepository userRepository, final JwtService jwtService,
                        final TokenRepository tokenRepository, final ProfileRepository profileRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.profileRepository = profileRepository;
    }

    @Override
    public SingInOutput execute(final SingInInput input) {
        input.validatePosswordAndConfirmPassword();
        final ProfileEnt profile = this.checkProfile();

        final UserEnt user = UserEnt.create(input.name(), input.email(), this.passwordEncoder.encode(input.password()), profile);

        final String accessToken = this.createToken(user);
        final TokenEnt token = TokenEnt.create(accessToken, TokenType.BEARER, user);
        this.tokenRepository.save(token);

        user.getTokens().add(token);
        final UserEnt userSaved = this.userRepository.save(user);

        this.revokeTokens(userSaved);
        final String refreshToken = this.jwtService.generateRefreshToken(userSaved);
        return new SingInOutput(new AuthenticationDtoResponse(accessToken, refreshToken));
    }

    private String createToken(UserEnt user){
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("Id", user.getId());
        extraClaims.put("Tag", "User");
        extraClaims.put("Profile", user.getProfile().getName());
        extraClaims.put("Permissions", user.getProfile().getAuthoraties());

        return this.jwtService.generateAccessToken(extraClaims, user);
    }

    private void revokeTokens(UserEnt user){
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

    private ProfileEnt checkProfile(){
        ProfileEnt profile = this.profileRepository.findByName("users")
            .orElseGet(() -> ProfileEnt.create("user", "User profile with default permissions"));

        if (profile.getPermissions().isEmpty()){
            PermissionTypes.getPermissions("user").forEach(profile::addPermission);
        }

        this.profileRepository.save(profile);

        return profile;
    }
}
