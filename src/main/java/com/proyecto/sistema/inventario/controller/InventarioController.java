package com.proyecto.sistema.inventario.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.proyecto.sistema.inventario.entities.Categoria;
import com.proyecto.sistema.inventario.entities.Producto;
import com.proyecto.sistema.inventario.service.CategoriaService;
import com.proyecto.sistema.inventario.service.ProductoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/inventario")
public class InventarioController {
    private final CategoriaService categoriaService;
    private final ProductoService productoService;

    Integer stockMinimo = 10;

    @GetMapping
    public String vistaInventario(@RequestParam(required = false) String q,
            @RequestParam(required = false) Long categoriaId, Model model) {
        model.addAttribute("categoria", new Categoria());
        model.addAttribute("categorias", categoriaService.listarCategorias());
        model.addAttribute("producto", new Producto());
        model.addAttribute("productos", productoService.listarProductosFiltrados(q, categoriaId));
        model.addAttribute("q", q);
        model.addAttribute("categoriaId", categoriaId);
        model.addAttribute("totalProductos", productoService.contarProductos());
        model.addAttribute("bajoStock", productoService.bajoStock(stockMinimo));
        model.addAttribute("totalCategorias", categoriaService.contarCategorias());
        return "inventario";
    }

    @PostMapping("/categorias")
    public String agregarCategoria(@Valid @ModelAttribute("categoria") Categoria c, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("producto", new Producto());
            cargarDatosInventario(model);
            return "inventario";
        }
        categoriaService.guardarCategoria(c);
        return "redirect:/inventario";
    }

    @PostMapping("/productos")
    public String guardarProducto(@Valid @ModelAttribute("producto") Producto p, BindingResult result,
            @RequestParam MultipartFile archivoImagen, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categoria", new Categoria());
            cargarDatosInventario(model);
            return "inventario";
        }
        if (p.getId() == null && archivoImagen.isEmpty()) {
            result.reject("archivoImagen", "Debe seleccionar una imagen para el producto");
        }

        try {
            if (!archivoImagen.isEmpty()) {
                p.setImagen(archivoImagen.getBytes());
            } else if (p.getId() != null) {
                Producto existProduct = productoService.buscarPorId(p.getId());
                if (existProduct != null) {
                    p.setImagen(existProduct.getImagen());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        productoService.guardarProducto(p);
        return "redirect:/inventario";
    }

    @GetMapping("/productos/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return "redirect:/inventario";
    }

    @GetMapping("/categorias/eliminar/{id}")
    public String eliminarCategoria(@PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
        return "redirect:/inventario";
    }

    @GetMapping("/productos/sugerencia")
    @ResponseBody
    public List<String> sugerencia(@RequestParam String q) {
        return productoService.sugerirNombre(q);
    }

    // METODO ESPECIAL PARA IMPLEMENTAR EL VALIDATION
    private void cargarDatosInventario(Model model) {
        model.addAttribute("categorias", categoriaService.listarCategorias());
        model.addAttribute("productos", productoService.listarProductosFiltrados(null, null));
        model.addAttribute("totalProductos", productoService.contarProductos());
        model.addAttribute("bajoStock", productoService.bajoStock(stockMinimo));
        model.addAttribute("totalCategorias", categoriaService.contarCategorias());
        model.addAttribute("q", null);
        model.addAttribute("categoriaId", null);
    }
}
