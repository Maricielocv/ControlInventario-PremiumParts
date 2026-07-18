package com.proyecto.sistema.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.sistema.inventario.entities.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
}
