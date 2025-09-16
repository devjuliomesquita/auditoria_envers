package com.juliomesquita.demoauditoria.infra.configs.audit.listener;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditMethodInterceptor {

    private static final ThreadLocal<StringBuilder> methodChain = new ThreadLocal<>();

    @Pointcut("execution(* com.juliomesquita..*UCImpl.*(..))")
    public void ucImplMethods() {}

    @Before("ucImplMethods()")
    public void captureMethodName(JoinPoint joinPoint) {
        StringBuilder chain = methodChain.get();
        if (chain == null) {
            chain = new StringBuilder();
            methodChain.set(chain);
        }

        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        String fullName = className + "." + methodName;

        if (chain.length() > 0) {
            chain.append(", ");
        }
        chain.append(fullName);

        System.out.println("Cadeia de métodos interceptada: " + chain.toString());
    }

    public static void clearMethodName() {
        if (methodChain.get() != null) {
            System.out.println("Limpando ==> Cadeia de métodos: " + methodChain.get().toString());
            methodChain.remove();
        }
    }

    public static String getCurrentMethod() {
        StringBuilder chain = methodChain.get();
        return (chain != null) ? chain.toString() : null;
    }
}

