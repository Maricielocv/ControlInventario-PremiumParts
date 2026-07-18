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

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 40, message = "El nombre del producto no puede exceder los 40 caracteres")
    @Column(nullable = false)
    private String nombre;

    @NotNull(message = "La categoría del producto es obligatoria")
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @NotNull(message = "El stock del producto es obligatorio")
    @Min(value = 1, message = "El stock del producto no puede ser menor a 1")
    @Column(nullable = false)
    private Integer stock;

    @NotBlank(message = "El SKU del producto es obligatorio")
    @Size(max = 8, message = "El SKU del producto no puede exceder los 8 caracteres")
    @Column(unique = true)
    private String sku;

    @NotNull(message = "El precio del producto es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio del producto debe ser mayor a 0")
    @Column(nullable = false)
    private Double precio_compra;

    @NotNull(message = "El precio del producto es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio del producto debe ser mayor a 0")
    @Column(nullable = false)
    private Double precio_venta;

    @NotBlank(message = "La descripción del producto es obligatoria")
    @Size(max = 200, message = "La descripción del producto no puede exceder los 200 caracteres")
    @Column(nullable = false)
    private String descripcion;

    @Column(updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fecha_creacion;

    private LocalDateTime fecha_ultima_venta;

    @Column(nullable = false)
    private Integer veces_vendido = 0;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = true, columnDefinition = "LONGBLOB")
    private byte[] imagen;

    @Transient
    public Double getGanancia() {
        if (precio_venta != null && precio_compra != null) {
            return Math.round((precio_venta - precio_compra) * 100.0) / 100.0;
        }
        return 0.0;
    }

    public Double getGananciaActual (){
        if (precio_venta != null && veces_vendido > 0) {
            return Math.round(((precio_venta-precio_compra)* veces_vendido) * 100.0) / 100.0;
        }
        return 0.0;
    }

    public Double getGananciaTotal(){
        if (precio_venta != null && stock!= null) {
            return (double) (Math.round((stock*precio_venta)*100) / 100);
        }
        return 0.0;
    }

    @Transient
    public Double getInversion() {
        if (stock != null && precio_compra != null) {
            return Math.round(stock * precio_compra * 100.0) / 100.0;
        }
        return 0.0;
    }

    @Transient
    public String getImagenBase64() {
        if (this.imagen != null && this.imagen.length > 0) {
            return Base64.getEncoder().encodeToString(this.imagen);
        }
        return null;
    }

    @AssertTrue(message = "Debe seleccionar una categoría")
    public boolean isCategoriaSeleccionada() {
        return categoria != null && categoria.getId() != null;
    }

    @PrePersist
    public void prePersist() {
        this.fecha_creacion = LocalDateTime.now();
    }

}
