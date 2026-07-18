package com.proyecto.sistema.inventario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.proyecto.sistema.inventario.service.ProductoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PageController {
    private final ProductoService productoService;

    @GetMapping("/page")
    public String vistaPage(Model model) {
        model.addAttribute("fourProducts", productoService.listarProductosAleatorios());
        return "home";
    }
    @GetMapping("/")
    public String vistaDefault(Model model) {
        model.addAttribute("fourProducts", productoService.listarProductosAleatorios());
        return "home";
    }
}
