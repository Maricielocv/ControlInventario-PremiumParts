package com.proyecto.sistema.inventario.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyecto.sistema.inventario.entities.Venta;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    @Query("SELECT SUM(v.total) FROM Venta v WHERE v.fechaVenta = :fecha")    
    Double montoVentaDiario (@Param("fecha") LocalDate fecha);

    List<Venta> findAll();
}
