package com.proyecto.sistema.inventario.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Log antes de realizar la ejecucion de los metodos de VentaService
    @Before("execution(* com.proyecto.sistema.inventario.service.VentaService.*(..))")
    public void logBeforeVentaService(JoinPoint joinPoint) {
        logger.info("Ejecutando metodo: {} con argumentos: {}",
                joinPoint.getSignature().getName(),
                java.util.Arrays.toString(joinPoint.getArgs()));
    }
    // Log antes de realizar la ejecucion de los metodos de ProductoService
    @Before("execution(* com.proyecto.sistema.inventario.service.ProductoService.*(..))")
    public void logBeforeProductoService(JoinPoint joinPoint) {
        logger.info("Ejecutando metodo: {} con argumentos: {}",
                joinPoint.getSignature().getName(),
                java.util.Arrays.toString(joinPoint.getArgs()));
    }

    // Log para venta exitosa
    @AfterReturning(pointcut = "execution(* com.proyecto.sistema.inventario.service.VentaService.completarVenta(..))", returning = "result")
    public void logAfterVentaCompletada(JoinPoint joinPoint, Object result) {
        logger.info("Venta completada con exito: {}", result);
    }


    // Log para crear  productos exitosamente
    @AfterReturning(pointcut = "execution(* com.proyecto.sistema.service.guardarProducto(..))", returning = "result")
    public void logAfterAgregarProducto(JoinPoint joinPoint, Object result){
        logger.info("Producto creado existosamente: {}" , result);
    }


    // Log de excepciones de ventas
    @AfterThrowing(pointcut = "execution(* com.proyecto.sistema.inventario.service.VentaService.*(..))", throwing = "error")
    public void logAfterThrowingVenta(JoinPoint joinPoint, Throwable error) {
        logger.error("Error en método {}: {}", joinPoint.getSignature().getName(), error.getMessage());
    }

}
