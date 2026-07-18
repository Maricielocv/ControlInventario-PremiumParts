package com.proyecto.sistema.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.sistema.inventario.entities.VentaDetalle;

public interface VentaDetalleRepository extends JpaRepository<VentaDetalle, Long> {
    
}
