package com.proyecto.sistema.inventario.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "empleados")
@NoArgsConstructor
public class Empleado extends Usuario {
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Debe seleccionar un rol")
    private Rol rol;

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
}
