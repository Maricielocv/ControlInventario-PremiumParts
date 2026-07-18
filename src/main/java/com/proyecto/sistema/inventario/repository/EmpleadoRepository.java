package com.proyecto.sistema.inventario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.sistema.inventario.entities.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    Optional<Empleado> findByEmail(String email);
    // SE AGREGO PARA LA RECUPERACION DE LA CONTRASEÑA
    Optional<Empleado> findByTokenPassword(String tokenPassword);
}
