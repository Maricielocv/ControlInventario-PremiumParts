package com.proyecto.sistema.inventario.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.proyecto.sistema.inventario.entities.Producto;
import com.proyecto.sistema.inventario.entities.Venta;
import com.proyecto.sistema.inventario.repository.ProductoRepository;
import com.proyecto.sistema.inventario.service.ProductoService;
import com.proyecto.sistema.inventario.service.VentaService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/ventas")
@SessionAttributes("ventaActual")
public class VentasController {
    private final ProductoRepository productoRepository;

    private final VentaService ventaService;

    private final ProductoService productoService;

    LocalDate fechaActual = LocalDate.now();

    @ModelAttribute("ventaActual")
    public Venta ventaActual() {
        Venta v = new Venta();
        return v;
    }

    @GetMapping
    public String vistaVentas(@ModelAttribute("ventaActual") Venta venta, Model model) {
        ventaService.recalcularTotales(venta);
        model.addAttribute("venta", venta);
        model.addAttribute("promedioVenta", ventaService.promedioVenta());
        model.addAttribute("ventasCompletadas", ventaService.ventasCompletadas());
        model.addAttribute("montoVentaDiaria", ventaService.montoVentaDiaria(fechaActual));
        return "ventas";
    }

    @PostMapping("/agregar")
    public String agregarDetalle(
            @RequestParam String nombreProducto,
            @RequestParam Integer cantidad,
            @RequestParam(required = false) Double descuento,
            @ModelAttribute("ventaActual") Venta v, RedirectAttributes redirectAttributes) {
        try {
            ventaService.agregarDetalle(v, nombreProducto, cantidad, descuento);

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/ventas";
    }

    @PostMapping("/eliminar")
    public String eliminarDetalle(
            @RequestParam Long productoId,
            @ModelAttribute("ventaActual") Venta v) {
        ventaService.eliminarDetalle(v, productoId);
        return "redirect:/ventas";
    }

    @PostMapping("/completar")
    public String completarVenta(
            @RequestParam String metodoPago,
            @RequestParam String tipoRecibo,
            @RequestParam(required = false, defaultValue = "0.0") double pagoRecibido,
            @ModelAttribute("ventaActual") Venta v, SessionStatus status) {

        ventaService.completarVenta(v, metodoPago, tipoRecibo, pagoRecibido);
        status.setComplete();
        return "redirect:/ventas";
    }

    @GetMapping("/productos/sugerencia")
    @ResponseBody
    public List<String> sugerencia(@RequestParam String q) {
        return productoService.sugerirNombre(q);
    }

    // MOSTRAR PRECIO DEL PRODUCTO DURANTE LA VENTA
    @GetMapping("/venta/precio")
    @ResponseBody
    public double mostrarPrecio(@RequestParam String nombreProducto) {
        Producto producto = productoRepository.findByNombre(nombreProducto).stream().findFirst().orElseThrow();
        return producto != null ? producto.getPrecio_venta() : 0.0;
    }

    // MOSTRAR EL STOCK DEL PRODUCTO DURANTE LA VENTA
    @GetMapping("/venta/stock")
    @ResponseBody
    public Integer mostrarStock(@RequestParam String nombreProducto) {
        Producto producto = productoRepository.findByNombre(nombreProducto).stream().findFirst().orElseThrow();
        return producto != null ? producto.getStock() : 0;
    }

    // MOSTRAR TOTAL DE LA VENTA ACTUAL
    // ESTO SIRVE PARA PODER CALCULAR EL VUELTO DE LA VENTA SI EL METODO DE PAGO ES
    // 'EFECTIVO'
    // LA LOGICA DEL VUELTO DE LA VENTA ESTA EN EL ARCHIVO venta.js (line 32)
    @GetMapping("/venta/total")
    @ResponseBody
    public double mostrarTotal(@ModelAttribute("ventaActual") Venta v) {
        return v.getTotal();
    }

}
