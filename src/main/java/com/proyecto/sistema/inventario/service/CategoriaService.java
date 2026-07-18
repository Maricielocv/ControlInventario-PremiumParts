package com.proyecto.sistema.inventario.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proyecto.sistema.inventario.entities.Categoria;
import com.proyecto.sistema.inventario.repository.CategoriaRepository;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> listarCategorias(){
        return categoriaRepository.findAll();
    }

     public void eliminarCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }

    public Categoria guardarCategoria(Categoria c){
        return categoriaRepository.save(c);
    }

    public Long contarCategorias(){
        return categoriaRepository.count();
    }
}
