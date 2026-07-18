package com.proyecto.sistema.inventario.service;

import com.proyecto.sistema.inventario.repository.ProductoRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.proyecto.sistema.inventario.entities.Producto;

@Service
public class ReporteService {

    private final ProductoService productoService;
    private final ProductoRepository productoRepository;

    public ReporteService(ProductoService productoService, ProductoRepository productoRepository) {
        this.productoService = productoService;
        this.productoRepository = productoRepository;
    }

    public Double valorTotalInversion() {
        List<Producto> p = productoService.listarProductos();
        return p.stream().mapToDouble(pro -> pro.getStock() * pro.getPrecio_compra()).sum();
    }

    public Double valorPotencialVenta() {
        List<Producto> p = productoService.listarProductos();
        return p.stream().mapToDouble(pro -> pro.getStock() * pro.getPrecio_venta()).sum();
    }

    public Double gananciaPotencial() {
        return (double) (Math.round((valorPotencialVenta() - valorTotalInversion()) * 100) / 100);
    }

    public List<Producto> top10Productos() {
        return productoRepository.findTop10MasVendidos(PageRequest.of(0, 10));
    }

    public Map<String, Long> productosPorCategoria() {
        List<Object[]> resultados = productoRepository.productosPorCategoria();
        return resultados.stream().collect(Collectors.toMap(r -> (String) r[0], r -> (Long) r[1]));
    }
}
