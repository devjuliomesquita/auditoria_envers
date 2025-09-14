package com.juliomesquita.demoAuditoria.infra.configs.audit.listener;

import com.juliomesquita.demoAuditoria.data.entities.RevisionAuditedEnt;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.envers.RevisionListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.UUID;

@Component
public class RevisionAuditedListener implements RevisionListener {
    @Override
    public void newRevision(Object o) {
        RevisionAuditedEnt revisionAudited = (RevisionAuditedEnt) o;

//        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
//            revisionAuditedEntity.setActionDoneBy(null);
//        }

//        final Object principal = authentication.getPrincipal();

//        if (principal instanceof Jwt jwt) {
//            final String actionDoneBy = jwt.getClaim("sub") != null ? jwt.getClaim("sub") : jwt.getSubject();
//            revisionAuditedEntity.setActionDoneBy(UUID.fromString(actionDoneBy));
//        }

        revisionAudited.setActionDoneBy(UUID.randomUUID());
        revisionAudited.setNameActionDoneBy("Anonymous");
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
