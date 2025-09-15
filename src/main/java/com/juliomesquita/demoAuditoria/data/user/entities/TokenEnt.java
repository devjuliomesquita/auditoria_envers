package com.juliomesquita.demoAuditoria.data.user.entities;

import com.juliomesquita.demoAuditoria.data.entities.BaseEntityWithGeneratedId;
import com.juliomesquita.demoAuditoria.data.user.enums.TokenType;
import jakarta.persistence.*;

@Entity
@Table(name = "tokens")
public class TokenEnt extends BaseEntityWithGeneratedId {

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

    public static TokenEnt create(String value, TokenType tokenType,  UserEnt user){
        return new TokenEnt(value, tokenType, false, false, user);
    }

    private TokenEnt(String value, TokenType tokenType, boolean expired, boolean revoked, UserEnt user) {
        this.value = value;
        this.tokenType = tokenType;
        this.expired = expired;
        this.revoked = revoked;
        this.user = user;
    }

    protected TokenEnt() {

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
