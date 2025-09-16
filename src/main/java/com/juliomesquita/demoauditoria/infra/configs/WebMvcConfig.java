package com.juliomesquita.demoauditoria.infra.configs;

import com.juliomesquita.demoauditoria.infra.configs.audit.listener.AuditCleanupInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuditCleanupInterceptor auditCleanupInterceptor;

    public WebMvcConfig(AuditCleanupInterceptor auditCleanupInterceptor) {
        this.auditCleanupInterceptor = auditCleanupInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(auditCleanupInterceptor);
    }
}
