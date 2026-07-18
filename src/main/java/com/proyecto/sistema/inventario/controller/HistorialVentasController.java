package com.proyecto.sistema.inventario.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.proyecto.sistema.inventario.entities.Venta;
import com.proyecto.sistema.inventario.service.VentaService;

@Controller
public class HistorialVentasController {

    private final VentaService ventaService;
    
    public HistorialVentasController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @GetMapping("/historial")
    public String vistaHistorialVentas(Model model){
        List<Venta> ventas = ventaService.listadoDeVentas();
        model.addAttribute("listaVentas", ventas);
        model.addAttribute("cantidadVentas", ventas.size());
        model.addAttribute("ingresoTotal", ventaService.ingresoTotal());
        model.addAttribute("promedioVentas", ventaService.promedioVenta());
        return "historial-ventas";
    }

    @GetMapping("/historial/detalleVenta/{id}")
    public String detalleVenta(Model model,@PathVariable Long id){
        List<Venta> ventas = ventaService.listadoDeVentas();
        model.addAttribute("listaVentas", ventas);
        model.addAttribute("cantidadVentas", ventas.size());
        model.addAttribute("venta", ventaService.obtenerVentaPorId(id));
        return "historial-ventas";
    }
}
