package com.juliomesquita.demoAuditoria.data.user.entities;

import jakarta.persistence.*;
import org.flywaydb.core.internal.parser.TokenType;

import java.util.UUID;

@Entity
@Table(name = "tb_token")
public class TokenEnt {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "token_id", nullable = false)
    private UUID id;

    @Column(name = "token_value", length = 2000)
    private String value;

    @Enumerated(EnumType.STRING)
    @Column(name = "token_type")
    private TokenType tokenType;

    @Column(name = "token_expired")
    private boolean expired;

    @Column(name = "token_revoked")
    private boolean revoked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserEnt user;

    public UUID getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public boolean isExpired() {
        return expired;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public UserEnt getUser() {
        return user;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }
}
