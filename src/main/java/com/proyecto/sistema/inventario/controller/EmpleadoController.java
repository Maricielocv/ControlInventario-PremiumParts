package com.proyecto.sistema.inventario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.sistema.inventario.entities.Empleado;
import com.proyecto.sistema.inventario.service.EmpleadoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    @GetMapping
    public String vistaEmpleados(Model model) {
        model.addAttribute("listar", empleadoService.listarEmpleados());
        model.addAttribute("empleado", new Empleado());
        return "empleados";
    }

    @PostMapping("/guardar")
    public String guardarEmpleado(@Valid @ModelAttribute Empleado empleado, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("listar", empleadoService.listarEmpleados());
            return "empleados";
        }
        empleadoService.guardarEmpleado(empleado);
        return "redirect:/empleados";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarEmpleado(@PathVariable Long id) {
        empleadoService.eliminarEmpleado(id);
        return "redirect:/empleados";
    }

}
