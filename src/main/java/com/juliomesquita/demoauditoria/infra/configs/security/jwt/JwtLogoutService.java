package com.juliomesquita.demoauditoria.infra.configs.security.jwt;

import com.juliomesquita.demoauditoria.data.user.entities.TokenEnt;
import com.juliomesquita.demoauditoria.data.user.repositories.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class JwtLogoutService implements LogoutHandler {
    private final TokenRepository tokenRepository;

    public JwtLogoutService(final TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);
        TokenEnt tokenSaved = this.tokenRepository.findByValue(jwt).orElse(null);
        if (tokenSaved != null) {
            tokenSaved.setExpired(true);
            tokenSaved.setRevoked(true);
            this.tokenRepository.save(tokenSaved);
        }
    }
}
