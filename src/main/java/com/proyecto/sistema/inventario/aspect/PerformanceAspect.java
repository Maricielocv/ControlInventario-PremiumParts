package com.proyecto.sistema.inventario.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceAspect {

    // VERIFICAR SI HAY METODOS QUE TARDAN MAS DE 1 SEGUNDO EN EJECUTARSE

    private static final Logger logger = LoggerFactory.getLogger(PerformanceAspect.class);

    public Object tiempoEjecucion(ProceedingJoinPoint joinPoint) throws Throwable {
        long inicio = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long tiempoTotal = System.currentTimeMillis() - inicio;
        logger.info("Metodo {} ejecutado en {} ms",
                joinPoint.getSignature().getName(), tiempoTotal);

        if (tiempoTotal > 1000) {
            logger.warn("ALERTA: Metodo {} tardó demasiado ({} ms)", joinPoint.getSignature().getName(), tiempoTotal);
        }

        return result;
    }
}
