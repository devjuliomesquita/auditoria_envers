package com.juliomesquita.demoauditoria.infra.configs.audit.listener;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditMethodInterceptor {

    private static final ThreadLocal<String> currentMethod = new ThreadLocal<>();

    @Pointcut("execution(* com.juliomesquita..*UCImpl.*(..))")
    public void ucImplMethods() {}

    @Before("ucImplMethods()")
    public void captureMethodName(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        String fullName = className + "." + methodName;
        currentMethod.set(fullName);

        System.out.println("Interceptado: " + fullName);
    }

    @After("ucImplMethods()")
    public void clearMethodName() {
        currentMethod.remove();
    }

    public static String getCurrentMethod() {
        return currentMethod.get();
    }
}

