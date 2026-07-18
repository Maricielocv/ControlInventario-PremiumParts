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

    /**
     * Construye el servicio de reportes con las dependencias requeridas.
     *
     * @param productoService     servicio para operaciones con productos
     * @param productoRepository  repositorio de productos
     */
    public ReporteService(ProductoService productoService, ProductoRepository productoRepository) {
        this.productoService = productoService;
        this.productoRepository = productoRepository;
    }

    /**
     * Calcula el valor total de la inversión en inventario.
     *
     * @return suma del (stock * precio de compra) de todos los productos
     */
    public Double valorTotalInversion() {
        List<Producto> p = productoService.listarProductos();
        return p.stream().mapToDouble(pro -> pro.getStock() * pro.getPrecio_compra()).sum();
    }

    /**
     * Calcula el valor potencial total de venta del inventario.
     *
     * @return suma del (stock * precio de venta) de todos los productos
     */
    public Double valorPotencialVenta() {
        List<Producto> p = productoService.listarProductos();
        return p.stream().mapToDouble(pro -> pro.getStock() * pro.getPrecio_venta()).sum();
    }

    /**
     * Calcula la ganancia potencial redondeada a dos decimales.
     *
     * @return diferencia entre {@code valorPotencialVenta} y {@code valorTotalInversion} redondeada
     */
    public Double gananciaPotencial() {
        return (double) (Math.round((valorPotencialVenta() - valorTotalInversion()) * 100) / 100);
    }

    /**
     * Obtiene los 10 productos más vendidos.
     *
     * @return lista de los 10 productos con mayor cantidad de ventas
     */
    public List<Producto> top10Productos() {
        return productoRepository.findTop10MasVendidos(PageRequest.of(0, 10));
    }

    /**
     * Obtiene la cantidad de productos agrupados por categoría.
     *
     * @return mapa donde la clave es el nombre de la categoría y el valor la cantidad de productos
     */
    public Map<String, Long> productosPorCategoria() {
        List<Object[]> resultados = productoRepository.productosPorCategoria();
        return resultados.stream().collect(Collectors.toMap(r -> (String) r[0], r -> (Long) r[1]));
    }
}
