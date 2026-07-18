package com.proyecto.sistema.inventario.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyecto.sistema.inventario.entities.Empleado;
import com.proyecto.sistema.inventario.service.EmailService;
import com.proyecto.sistema.inventario.service.EmpleadoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class RecuperarPasswordController {

    private final EmpleadoService empleadoService;
    private final EmailService emailService; 
    
    @GetMapping("/recuperar")
    public String vistaRecuperar() {
        return "recuperar-contrasena";
    }

    @PostMapping("/recuperar")
    public String enviarCorreo(@RequestParam String email, Model model) {
        Optional<Empleado> empleadoOpt = empleadoService.buscarPorEmail(email);
        if (empleadoOpt.isEmpty()) {
            model.addAttribute("error", "No existe ningún empleado con ese correo");
            return "recuperar-contrasena";
        }
        Empleado empleado = empleadoOpt.get();
        String token = UUID.randomUUID().toString();
        empleado.setTokenPassword(token);
        empleadoService.guardarEmpleado(empleado);
        emailService.enviarCorreoRecuperacion(empleado.getEmail(), empleado.getNombre(), token);
        model.addAttribute("mensaje", "Te enviamos un correo con las instrucciones");
        return "recuperar-contrasena";
    }

    @GetMapping("/reset-password")
    public String vistaReset(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        return "resetear-contrasena";
    }

    @PostMapping("/reset-password")
    public String cambiarPassword(@RequestParam String token,
                                   @RequestParam String password,
                                   @RequestParam String confirmPassword,
                                   Model model) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("token", token);
            model.addAttribute("error", "Las contraseñas no coinciden");
            return "resetear-contrasena";
        }
        Optional<Empleado> empleadoOpt = empleadoService.buscarPorTokenPassword(token);
        if (empleadoOpt.isEmpty()) {
            model.addAttribute("error", "Enlace inválido o ya usado");
            return "resetear-contrasena";
        }
        Empleado empleado = empleadoOpt.get();
        empleado.setPassword(password); 
        empleado.setTokenPassword(null); 
        empleadoService.guardarEmpleado(empleado);
        return "redirect:/login";
    }
}