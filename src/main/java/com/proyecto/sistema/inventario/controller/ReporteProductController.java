package com.proyecto.sistema.inventario.controller;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.sistema.inventario.service.ProductoService;
import com.proyecto.sistema.inventario.service.ReporteService;

@Controller
@RequestMapping("/reporte")
public class ReporteProductController {

    private final ProductoService productoService;
    private final ReporteService reporteService;

    public ReporteProductController(ProductoService productoService, ReporteService reporteService) {
        this.productoService = productoService;
        this.reporteService = reporteService;
    }

    @GetMapping
    public String vistaReportes(Model model) {
        model.addAttribute("listarProductos", productoService.listarProductos());
        model.addAttribute("totalInversion", reporteService.valorTotalInversion());
        model.addAttribute("ventaPotencial", reporteService.valorPotencialVenta());
        model.addAttribute("gananciaPotencial", reporteService.gananciaPotencial());
        model.addAttribute("top10",reporteService.top10Productos());
        model.addAttribute("categorias", reporteService.productosPorCategoria().entrySet());
        return "reporte-productos";
    }
}
