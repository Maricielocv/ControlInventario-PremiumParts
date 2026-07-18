package com.proyecto.sistema.inventario.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        String rol = authentication.getAuthorities().iterator().next().getAuthority();
        switch (rol) {
            case "ROLE_ADMINISTRADOR" -> response.sendRedirect("/empleados");
            case "ROLE_VENDEDOR" -> response.sendRedirect("/ventas");
            case "ROLE_ALMACENERO" -> response.sendRedirect("/inventario");
            default -> response.sendRedirect("/page");
        }
    }

}
