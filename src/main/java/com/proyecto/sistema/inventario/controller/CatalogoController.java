package com.proyecto.sistema.inventario.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proyecto.sistema.inventario.entities.Producto;
import com.proyecto.sistema.inventario.service.CategoriaService;
import com.proyecto.sistema.inventario.service.ProductoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/catalogo")
public class CatalogoController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    @GetMapping
    public String vistaCatalogo(Model model, @RequestParam(required = false) String ordenP,
            @RequestParam(required = false) String q, @RequestParam(required = false) Long categoriaId,
            @RequestParam(defaultValue = "0") int page) {

        // Definimos el tamaño de la página en 20 elementos de forma nativa
        Pageable pageable = PageRequest.of(page, 20);
        Page<Producto> paginaProductos = productoService.listarProductosOptimizado(q, categoriaId, ordenP, pageable);

        model.addAttribute("filtroMejorado", paginaProductos.getContent());
        model.addAttribute("currentPage", page); // se agregó
        model.addAttribute("totalPages", paginaProductos.getTotalPages());// se agregó
        model.addAttribute("totalElementos", paginaProductos.getTotalElements()); // se agregó
        model.addAttribute("categorias", categoriaService.listarCategorias());
        model.addAttribute("categoriaId", categoriaId);
        model.addAttribute("ordenP", ordenP);// se agregó
        model.addAttribute("q", q);// se agregó
        return "Catalogo";
    }

    @GetMapping("/catalogo/sugerencia")
    @ResponseBody
    public List<String> sugerencia(@RequestParam String q) {
        return productoService.sugerirNombre(q);
    }
}
