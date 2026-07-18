package com.proyecto.sistema.inventario.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.proyecto.sistema.inventario.entities.Empleado;
import com.proyecto.sistema.inventario.entities.Rol;
import com.proyecto.sistema.inventario.repository.EmpleadoRepository;


@Service
public class EmpleadoService {
    private final EmpleadoRepository empleadoRepository;
    private final PasswordEncoder passwordEncoder;

    public EmpleadoService(EmpleadoRepository empleadoRepository, PasswordEncoder passwordEncoder) {
        this.empleadoRepository = empleadoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Empleado> listarEmpleados(){
        return empleadoRepository.findAll();
    }

    public void guardarEmpleado (Empleado empleado){
        String password = empleado.getPassword();
        if (password == null || password.isEmpty() || !password.startsWith("$2")) {
            empleado.setPassword(passwordEncoder.encode(password));
        }
        empleadoRepository.save(empleado);
    }

    public void eliminarEmpleado (Long id){
        empleadoRepository.deleteById(id);
    }

    public Empleado asignarRol(Long id, Rol rol){
        Empleado emp = empleadoRepository.findById(id).orElseThrow();
        emp.setRol(rol);
        return empleadoRepository.save(emp);
    }

    // SE AGREGO PARA LA RECUPERACION DE LA CONTRASEÑA
    public Optional<Empleado> buscarPorEmail(String email) {
    return empleadoRepository.findByEmail(email);
    }

    public Optional<Empleado> buscarPorTokenPassword(String token) {
    return empleadoRepository.findByTokenPassword(token);
    }
}
