package com.proyecto.sistema.inventario.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.proyecto.sistema.inventario.entities.Empleado;
import com.proyecto.sistema.inventario.entities.Rol;
import com.proyecto.sistema.inventario.repository.EmpleadoRepository;


/**
 * Servicio encargado de la gestión de empleados en el sistema de inventario.
 *
 * <p>Proporciona operaciones CRUD para empleados, así como funcionalidades
 * adicionales como asignación de roles y recuperación de contraseña
 * mediante token.</p>
 *
 * @see EmpleadoRepository
 * @see Empleado
 */
@Service
public class EmpleadoService {
    private final EmpleadoRepository empleadoRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor del servicio de empleados.
     *
     * @param empleadoRepository el repositorio de acceso a datos de empleados
     * @param passwordEncoder el codificador de contraseñas para encriptar credenciales
     */
    public EmpleadoService(EmpleadoRepository empleadoRepository, PasswordEncoder passwordEncoder) {
        this.empleadoRepository = empleadoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Obtiene la lista de todos los empleados registrados en el sistema.
     *
     * @return una lista con todos los empleados existentes
     */
    public List<Empleado> listarEmpleados(){
        return empleadoRepository.findAll();
    }

    /**
     * Guarda o actualiza un empleado en el sistema.
     *
     * <p>Si la contraseña proporcionada no está encriptada (no comienza con {@code "$2"}),
     * se encripta automáticamente antes de persistir el empleado.</p>
     *
     * @param empleado el empleado a guardar o actualizar
     */
    public void guardarEmpleado (Empleado empleado){
        String password = empleado.getPassword();
        if (password == null || password.isEmpty() || !password.startsWith("$2")) {
            empleado.setPassword(passwordEncoder.encode(password));
        }
        empleadoRepository.save(empleado);
    }

    /**
     * Elimina un empleado del sistema por su identificador.
     *
     * @param id el identificador único del empleado a eliminar
     */
    public void eliminarEmpleado (Long id){
        empleadoRepository.deleteById(id);
    }

    /**
     * Asigna un rol a un empleado específico.
     *
     * @param id el identificador único del empleado
     * @param rol el rol a asignar al empleado
     * @return el empleado con el rol actualizado
     * @throws java.util.NoSuchElementException si no se encuentra un empleado con el ID proporcionado
     */
    public Empleado asignarRol(Long id, Rol rol){
        Empleado emp = empleadoRepository.findById(id).orElseThrow();
        emp.setRol(rol);
        return empleadoRepository.save(emp);
    }

    /**
     * Busca un empleado por su dirección de correo electrónico.
     *
     * @param email el correo electrónico del empleado a buscar
     * @return un {@link Optional} que contiene el empleado si se encuentra, o vacío si no existe
     */
    public Optional<Empleado> buscarPorEmail(String email) {
    return empleadoRepository.findByEmail(email);
    }

    /**
     * Busca un empleado por su token de recuperación de contraseña.
     *
     * @param token el token de recuperación de contraseña
     * @return un {@link Optional} que contiene el empleado asociado al token, o vacío si no se encuentra
     */
    public Optional<Empleado> buscarPorTokenPassword(String token) {
    return empleadoRepository.findByTokenPassword(token);
    }
}
