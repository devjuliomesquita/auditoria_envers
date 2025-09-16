package com.juliomesquita.demoauditoria.infra.configs.audit.listener;

import com.juliomesquita.demoauditoria.data.audit.entities.RevisionAuditedEnt;
import com.juliomesquita.demoauditoria.data.user.entities.UserEnt;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.envers.RevisionListener;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Component
public class RevisionAuditedListener implements RevisionListener {
    @Override
    public void newRevision(Object o) {
        RevisionAuditedEnt revisionAudited = (RevisionAuditedEnt) o;

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            revisionAudited.setActionDoneBy(null);
            revisionAudited.setNameActionDoneBy("Usuário Anónimo.");
        }

        final Object principal = Objects.requireNonNull(authentication).getPrincipal();

        if (principal instanceof UserEnt user) {
            final String nameActionDoneBy = user.getName();
            revisionAudited.setNameActionDoneBy(nameActionDoneBy);
            revisionAudited.setActionDoneBy(user.getId());
        }
        System.out.println("RevisionAuditedListener.newRevision ==> " + principal);
        final String method = AuditMethodInterceptor.getCurrentMethod();
        revisionAudited.setMethodNamesTracking(method);

        revisionAudited.setActionDoneByIp(this.getClientIp());
    }

    private String getClientIp() {
        HttpServletRequest request =
            ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
