package com.proyecto.sistema.inventario.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.proyecto.sistema.inventario.entities.Producto;

@Aspect
@Component
public class AuditoriaAspect {

    // ASPECTOS PARA REGISTRAR CAMBIOS DENTRO DEL MODULO DE PRODUCTOS
    // SOLO REGISTRA CAMBIOS AL EDITAR , CREAR Y ELIMINAR PRODUCTOS

    private static final Logger logger = LoggerFactory.getLogger(AuditoriaAspect.class);

    @AfterReturning(pointcut = "execution(* com.proyecto.sistema.inventario.service.ProductoService.guardarProducto(..))", returning = "producto")
    public void auditarProductoGuardado(JoinPoint joinPoint, Producto producto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String usuario = auth != null ? auth.getName() : "sistema";

        logger.info("AUDITORIA: Producto '{}' guardado / actualizado por usuario: {} - Precio: {}, Stock: {}",
                producto.getNombre(), usuario, producto.getPrecio_venta(), producto.getStock());
    }

    @AfterReturning("execution(* com.proyecto.sistema.inventario.service.ProductoService.eliminarProducto(..)) && args(id)")
    public void auditarEliminacionProducto(JoinPoint joinPoint, Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String usuario = auth != null ? auth.getName() : "sistema";

        logger.warn("AUDITORÍA: Producto con ID {} eliminado por usuario: {}", id, usuario);
    }
}
