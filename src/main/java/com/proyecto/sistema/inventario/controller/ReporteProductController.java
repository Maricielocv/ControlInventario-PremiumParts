package com.proyecto.sistema.inventario.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proyecto.sistema.inventario.service.CategoriaService;
import com.proyecto.sistema.inventario.service.ProductoService;
import com.proyecto.sistema.inventario.service.ReporteService;

@Controller
@RequestMapping("/reporte")
public class ReporteProductController {

    private final ProductoService productoService;
    private final ReporteService reporteService;
    private final CategoriaService categoriaService;

    public ReporteProductController(ProductoService productoService, ReporteService reporteService,
            CategoriaService categoriaService) {
        this.productoService = productoService;
        this.reporteService = reporteService;
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String vistaReportes(Model model, @RequestParam(required = false) String q,
            @RequestParam(required = false) Long categoriaId) {
        model.addAttribute("listarProductos", productoService.listarProductosFiltrados(q, categoriaId));
        model.addAttribute("totalInversion", reporteService.valorTotalInversion());
        model.addAttribute("ventaPotencial", reporteService.valorPotencialVenta());
        model.addAttribute("gananciaPotencial", reporteService.gananciaPotencial());
        model.addAttribute("categoriasListado", categoriaService.listarCategorias());
        model.addAttribute("top10", reporteService.top10Productos());
        model.addAttribute("categorias", reporteService.productosPorCategoria().entrySet());
        return "reporte-productos";
    }

    @GetMapping("/sugerencia")
    @ResponseBody
    public List<String> sugerencia(@RequestParam String q) {
        return productoService.sugerirNombre(q);
    }
}
