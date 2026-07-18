package com.proyecto.sistema.inventario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.sistema.inventario.entities.Empleado;
import com.proyecto.sistema.inventario.service.EmpleadoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginDashboardController {

    private final EmpleadoService empleadoService;

    @GetMapping
    public String vistaLogin(Model model){
        return "login";
    }


    @GetMapping("/registro")
    public String vistaRegistro(Model model){
        model.addAttribute("empleado", new Empleado());
        return "registroEmpleado";
    }

    @PostMapping("/registrar-empleado")
    public String saveEmploye (@Valid @ModelAttribute Empleado empleado, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("empleado", empleado);
            return "registroEmpleado";
        }
        empleadoService.guardarEmpleado(empleado);
        return "redirect:/login";
    }
    

    // @GetMapping
    // public String vistaLogin(Model model){
    //     model.addAttribute("empleado", new Empleado());
    //     return "login-dashboard";
    // }

}
