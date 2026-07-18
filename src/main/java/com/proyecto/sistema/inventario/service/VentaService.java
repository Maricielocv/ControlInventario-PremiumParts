package com.proyecto.sistema.inventario.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.proyecto.sistema.inventario.entities.Producto;
import com.proyecto.sistema.inventario.entities.Venta;
import com.proyecto.sistema.inventario.entities.VentaDetalle;
import com.proyecto.sistema.inventario.repository.ProductoRepository;
import com.proyecto.sistema.inventario.repository.VentaRepository;

import jakarta.transaction.Transactional;

@Service
public class VentaService {
    private final ProductoRepository productoRepository;
    private final VentaRepository ventaRepository;

    VentaService(ProductoRepository productoRepository, VentaRepository ventaRepository) {
        this.productoRepository = productoRepository;
        this.ventaRepository = ventaRepository;
    }

    @Transactional
    public Venta agregarDetalle(Venta v, String nombreProducto, Integer cantidad, Double descuentoPct) {

        Producto producto = productoRepository.findByNombre(nombreProducto).stream().findFirst().orElseThrow();

        // validacion nivel service para evitar registrar detalles con stock fuera de
        // limite (line 31-44)
        int stockDisponible = producto.getStock();
        // recorre los detalle de venta, filtra resultados de un mismo producto , suma
        // las cantidades que estan en el carrito
        int cantidadExistente = v.getDetalles().stream()
                .filter(d -> d.getProducto() != null && d.getProducto().getId().equals(producto.getId()))
                .mapToInt(VentaDetalle::getCantidad)
                .sum();
        int cantidadTotalSolicitada = cantidadExistente + cantidad;
        if (cantidadTotalSolicitada > stockDisponible) {
            throw new RuntimeException(
                    "Stock insuficiente para \"" + producto.getNombre()
                            + "\". Disponible: " + stockDisponible
                            + ", solicitado: " + cantidadTotalSolicitada
                            + (cantidadExistente > 0 ? " (ya tienes " + cantidadExistente + " en el carrito)" : ""));
        }

        double precio = producto.getPrecio_venta();
        double subtotalLinea = precio * cantidad;
        subtotalLinea = Math.round(subtotalLinea * 100.0) / 100.0;
        double descuentoLinea = (descuentoPct == null) ? 0 : subtotalLinea * descuentoPct / 100;
        descuentoLinea = Math.round(descuentoLinea * 100.0) / 100.0;
        double subtotalConDescuento = subtotalLinea - descuentoLinea;

        VentaDetalle detalle = new VentaDetalle();
        detalle.setVenta(v);
        detalle.setProducto(producto);
        detalle.setCantidad(cantidad);
        detalle.setPrecioUnitario(precio);
        detalle.setDescuento(descuentoLinea);
        detalle.setSubtotal(subtotalConDescuento);

        v.getDetalles().add(detalle);

        return v;

    }

    public void recalcularTotales(Venta v) {
        double subtotal = v.getDetalles().stream().mapToDouble(VentaDetalle::getSubtotal).sum();
        double descuento = v.getDetalles().stream().mapToDouble(d -> d.getDescuento() == null ? 0 : d.getDescuento())
                .sum();
        // double igv = (subtotal - descuento) * 0.18;
        // double igvFormateado = Math.round(igv * 100.0) / 100.0;
        double total = subtotal - descuento;
        total = Math.round(total * 100.0) / 100.0;

        v.setSubTotal(subtotal);
        v.setDescuento(descuento);
        // v.setIgv(igvFormateado);
        v.setTotal(total);
    }

    public void eliminarDetalle(Venta v, Long productoId) {
        boolean eliminado = v.getDetalles().removeIf(d -> d.getProducto() != null &&
                d.getProducto().getId() != null &&
                d.getProducto().getId().equals(productoId));
        if (eliminado) {
            recalcularTotales(v);
        }
    }

    public double promedioVenta() {
        List<Venta> ventas = ventaRepository.findAll();
        double sumaTotal = 0;
        double promedioventa = 0;
        for (Venta v : ventas) {
            sumaTotal += v.getTotal();
        }
        promedioventa = sumaTotal / ventas.size();
        promedioventa = Math.round(promedioventa * 100.0) / 100.0;
        return promedioventa;
    }

    @Transactional
    public void actualizarStock(Venta v) {
        for (VentaDetalle vd : v.getDetalles()) {
            Long productoId = vd.getProducto().getId();
            Producto p = productoRepository.findById(productoId).orElseThrow();

            int cantidad = vd.getCantidad();
            int nuevoStock = p.getStock() - cantidad;

            p.setStock(nuevoStock);
            p.setFecha_ultima_venta(LocalDateTime.now());
            p.setVeces_vendido(p.getVeces_vendido() + cantidad);
        }
    }

    @Transactional
    public Venta completarVenta(Venta v, String metodoPago, String tipoRecibo, double pagoRecibido) {
        if (pagoRecibido == 0.0) {
            pagoRecibido = v.getTotal();
        }
        double vuelto = pagoRecibido - v.getTotal();
        vuelto = Math.round(vuelto * 100.0) / 100.0;
        v.setVuelto(vuelto);
        v.setMetodoPago(metodoPago);
        v.setTipoRecibo(tipoRecibo);
        v.setPagoRecibido(pagoRecibido);
        actualizarStock(v);
        return ventaRepository.save(v);
    }

    public long ventasCompletadas() {
        return ventaRepository.count();
    }

    public Double montoVentaDiaria(LocalDate fecha) {
        Double total = ventaRepository.montoVentaDiario(fecha);
        if (total != null) {
            total = Math.round(total * 100.0) / 100.0;
        }
        return total != null ? total : 0.0;
    }

    public List<Venta> listadoDeVentas() {
        return ventaRepository.findAll();
    }

    public Venta obtenerVentaPorId(Long id) {
        return ventaRepository.findById(id).orElseThrow();
    }

    public double ingresoTotal() {
        List<Venta> ventas = ventaRepository.findAll();
        double ingresoTotal = ventas.stream().mapToDouble(Venta::getTotal).sum();
        return Math.round(ingresoTotal * 100.0) / 100.0;
    }
}