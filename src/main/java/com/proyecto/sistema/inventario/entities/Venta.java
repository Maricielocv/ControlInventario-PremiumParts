package com.proyecto.sistema.inventario.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ventas")
@Data
@NoArgsConstructor
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double subTotal;

    @Column(nullable = true)
    private Double descuento;

    // @Column(nullable = false)
    // private Double igv;

    @Column(nullable = false)
    private Double total;

    @Column(nullable = false)
    private String metodoPago;

    @Column(nullable = false)
    private String tipoRecibo;

    @Column(nullable = true)
    private Double pagoRecibido;

    @Column(nullable = true)
    private Double vuelto;

    private LocalDate fechaVenta = LocalDate.now();

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VentaDetalle> detalles = new ArrayList<>();
}
