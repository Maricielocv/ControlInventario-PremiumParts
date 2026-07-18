package com.proyecto.sistema.inventario.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proyecto.sistema.inventario.entities.Usuario;
import com.proyecto.sistema.inventario.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarUsuarios(){
        return usuarioRepository.findAll();
    }

    public void guardarUsuario (Usuario usuario){
        usuarioRepository.save(usuario);
    }

    public void eliminarUsuario (Long id){
        usuarioRepository.deleteById(id);
    }

}
