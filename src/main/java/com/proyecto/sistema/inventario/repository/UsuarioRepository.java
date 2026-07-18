package com.proyecto.sistema.inventario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.sistema.inventario.entities.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional <Usuario> findByEmail(String email);
}
