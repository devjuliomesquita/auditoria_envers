package com.juliomesquita.demoauditoria.data.user.entities;

import com.juliomesquita.demoauditoria.data.livro.entities.BaseEntityWithGeneratedId;
import com.juliomesquita.demoauditoria.data.user.enums.TokenType;
import jakarta.persistence.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

@Entity
@Table(name = "tokens")
@Audited
@AuditTable(value = "tokens_aud", schema = "core_audit")
public class TokenEnt extends BaseEntityWithGeneratedId {

    @Column(name = "token_value", length = 2000)
    private String value;

    @Enumerated(EnumType.STRING)
    @Column(name = "token_type")
    private TokenType tokenType;

    @NotAudited
    @Column(name = "token_expired")
    private boolean expired;

    @NotAudited
    @Column(name = "token_revoked")
    private boolean revoked;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
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
