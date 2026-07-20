package com.proyecto.sistema.inventario.entities;

import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.Base64;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad JPA que representa un producto dentro del inventario de autopartes.
 * <p>
 * Cada producto pertenece a una categoría, cuenta con un SKU único, precios de
 * compra y venta, control de stock, y una imagen opcional almacenada como
 * LONGBLOB. Provee métodos de cálculo para ganancias e inversión.
 * </p>
 */
@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
public class Producto {

    /** Identificador único generado automáticamente. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre del producto (máx. 40 caracteres, obligatorio). */
    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 40, message = "El nombre del producto no puede exceder los 40 caracteres")
    @Column(nullable = false)
    private String nombre;

    /** Categoría a la que pertenece el producto (obligatoria). */
    @NotNull(message = "La categoría del producto es obligatoria")
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    /** Cantidad disponible en inventario (mín. 1, obligatorio). */
    @NotNull(message = "El stock del producto es obligatorio")
    @Min(value = 1, message = "El stock del producto no puede ser menor a 1")
    @Column(nullable = false)
    private Integer stock;

    /** Código SKU único del producto (máx. 8 caracteres, obligatorio). */
    @NotBlank(message = "El SKU del producto es obligatorio")
    @Size(max = 8, message = "El SKU del producto no puede exceder los 8 caracteres")
    @Column(unique = true)
    private String sku;

    /** Precio de compra o costo del producto (obligatorio, mayor a 0). */
    @NotNull(message = "El precio del producto es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio del producto debe ser mayor a 0")
    @Column(nullable = false)
    private Double precio_compra;

    /** Precio de venta al público (obligatorio, mayor a 0). */
    @NotNull(message = "El precio del producto es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio del producto debe ser mayor a 0")
    @Column(nullable = false)
    private Double precio_venta;

    /** Descripción textual del producto (máx. 200 caracteres, obligatorio). */
    @NotBlank(message = "La descripción del producto es obligatoria")
    @Size(max = 200, message = "La descripción del producto no puede exceder los 200 caracteres")
    @Column(nullable = false)
    private String descripcion;

    /** Fecha y hora de creación del registro (se asigna automáticamente, no se actualiza). */
    @Column(updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fecha_creacion;

    /** Fecha y hora de la última venta registrada del producto. */
    private LocalDateTime fecha_ultima_venta;

    /** Número de veces que el producto ha sido vendido (por defecto 0). */
    @Column(nullable = false)
    private Integer veces_vendido = 0;

    /** Imagen del producto almacenada como LONGBLOB (carga diferida, opcional). */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = true, columnDefinition = "LONGBLOB")
    private byte[] imagen;

    /**
     * Calcula la ganancia unitaria del producto (precio_venta - precio_compra).
     *
     * @return ganancia unitaria redondeada a 2 decimales, o 0.0 si alguno de los
     *         precios es {@code null}.
     */
    @Transient
    public Double getGanancia() {
        if (precio_venta != null && precio_compra != null) {
            return Math.round((precio_venta - precio_compra) * 100.0) / 100.0;
        }
        return 0.0;
    }

    /**
     * Calcula la ganancia total generada por todas las ventas realizadas del
     * producto.
     *
     * @return ganancia acumulada redondeada a 2 decimales, o 0.0 si el precio de
     *         venta es {@code null} o el producto nunca se ha vendido.
     */
    public Double getGananciaActual() {
        if (precio_venta != null && veces_vendido > 0) {
            return Math.round(((precio_venta - precio_compra) * veces_vendido) * 100.0) / 100.0;
        }
        return 0.0;
    }

    /**
     * Calcula la ganancia potencial total si se vendiera todo el stock actual.
     *
     * @return ganancia total proyectada redondeada a 2 decimales, o 0.0 si el
     *         precio de venta o el stock son {@code null}.
     */
    public Double getGananciaTotal() {
        if (precio_venta != null && stock != null) {
            return (double) (Math.round((stock * precio_venta) * 100) / 100);
        }
        return 0.0;
    }

    /**
     * Calcula la inversión total en el stock actual del producto.
     *
     * @return inversión total redondeada a 2 decimales, o 0.0 si el stock o el
     *         precio de compra son {@code null}.
     */
    @Transient
    public Double getInversion() {
        if (stock != null && precio_compra != null) {
            return Math.round(stock * precio_compra * 100.0) / 100.0;
        }
        return 0.0;
    }

    /**
     * Convierte la imagen almacenada como arreglo de bytes a una cadena en
     * formato Base64 para su visualización en el frontend.
     *
     * @return cadena Base64 de la imagen, o {@code null} si no hay imagen.
     */
    @Transient
    public String getImagenBase64() {
        if (this.imagen != null && this.imagen.length > 0) {
            return Base64.getEncoder().encodeToString(this.imagen);
        }
        return null;
    }

    /**
     * Validación bean que verifica que se haya seleccionado una categoría
     * válida (no nula y con un ID asignado).
     *
     * @return {@code true} si la categoría existe y tiene ID, {@code false} en
     *         caso contrario.
     */
    @AssertTrue(message = "Debe seleccionar una categoría")
    public boolean isCategoriaSeleccionada() {
        return categoria != null && categoria.getId() != null;
    }

    /**
     * Asigna automáticamente la fecha de creación justo antes de persistir la
     * entidad por primera vez.
     */
    @PrePersist
    public void prePersist() {
        this.fecha_creacion = LocalDateTime.now();
    }

}
