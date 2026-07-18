package com.proyecto.sistema.inventario.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proyecto.sistema.inventario.entities.Usuario;
import com.proyecto.sistema.inventario.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;

    /**
     * Construye el servicio de usuarios con el repositorio requerido.
     *
     * @param usuarioRepository  repositorio para operaciones con usuarios
     */
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Obtiene todos los usuarios registrados.
     *
     * @return lista de todos los usuarios
     */
    public List<Usuario> listarUsuarios(){
        return usuarioRepository.findAll();
    }

    /**
     * Guarda un nuevo usuario o actualiza uno existente.
     *
     * @param usuario  entidad del usuario a guardar
     */
    public void guardarUsuario (Usuario usuario){
        usuarioRepository.save(usuario);
    }

    /**
     * Elimina un usuario por su identificador.
     *
     * @param id  identificador único del usuario a eliminar
     */
    public void eliminarUsuario (Long id){
        usuarioRepository.deleteById(id);
    }

}
