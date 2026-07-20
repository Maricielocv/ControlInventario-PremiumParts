package com.proyecto.sistema.inventario.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.sistema.inventario.entities.Producto;
import com.proyecto.sistema.inventario.repository.ProductoRepository;

/**
 * Proporciona operaciones de negocio para la gestión de productos del inventario.
 */
@Service
public class ProductoService {
    private final ProductoRepository productoRepository;

    /**
     * Construye el servicio con el repositorio de productos requerido.
     *
     * @param productoRepository repositorio para acceder a los productos
     */
    ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    /**
     * Obtiene todos los productos registrados.
     *
     * @return lista con todos los productos
     */
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    /**
     * Obtiene un subconjunto aleatorio de productos para la vista inicial.
     *
     * @return lista de hasta cuatro productos aleatorios
     */
    @Transactional(readOnly = true)
    public List<Producto> listarProductosAleatorios() {
        return productoRepository.listadoAleatorio(PageRequest.of(0, 4));
    }

    /**
     * Guarda o actualiza un producto en el repositorio.
     *
     * @param producto entidad a persistir
     * @return producto guardado con su identificador generado si aplica
     */
    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    /**
     * Cuenta la cantidad total de productos registrados.
     *
     * @return número total de productos
     */
    public Long contarProductos() {
        return productoRepository.count();
    }

    /**
     * Cuenta los productos cuyo stock es menor al valor indicado.
     *
     * @param stock límite máximo de stock para considerar el producto en bajo inventario
     * @return cantidad de productos con stock inferior al límite
     */
    public Long bajoStock(Integer stock) {
        return productoRepository.findByStockLessThan(stock).stream().count();
    }

    /**
     * Busca un producto por su identificador.
     *
     * @param id identificador del producto
     * @return producto encontrado o {@code null} si no existe
     */
    public Producto buscarPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    /**
     * Sugiere nombres de productos que coincidan con el texto proporcionado.
     *
     * @param q texto de búsqueda para filtrar nombres
     * @return lista de nombres que coinciden con la consulta
     */
    public List<String> sugerirNombre(String q) {
        String filtro = (q == null) ? "" : q.trim();
        return productoRepository.sugerirNombres(filtro, PageRequest.of(0, 10));
    }

    /**
     * Elimina un producto por su identificador.
     *
     * @param id identificador del producto a eliminar
     */
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

    /**
     * Lista productos aplicando filtros por nombre y categoría.
     *
     * @param q texto para filtrar por nombre
     * @param categoriaId identificador de la categoría para filtrar
     * @return lista de productos que cumplen los criterios de búsqueda
     */
    public List<Producto> listarProductosFiltrados(String q, Long categoriaId) {
        String nombre = (q == null) ? "" : q.trim();
        boolean tieneNombre = !nombre.isBlank();
        boolean tieneCategoria = (categoriaId != null);

        if (!tieneNombre && !tieneCategoria)
            return productoRepository.findAll();
        if (tieneNombre && !tieneCategoria)
            return productoRepository.findByNombre(nombre);
        if (!tieneNombre && tieneCategoria)
            return productoRepository.findByCategoriaId(categoriaId);

        return productoRepository.findByNombreAndCategoriaId(nombre, categoriaId);
    }

    /**
     * Lista productos filtrados y ordenados para la vista del catálogo.
     *
     * @param q texto para filtrar por nombre
     * @param categoriaId identificador de la categoría para filtrar
     * @param orden criterio de ordenamiento: asc, desc o stock
     * @param pageable información de paginación y tamaño de página
     * @return página de productos con el ordenamiento solicitado
     */
    public Page<Producto> listarProductosOptimizado(String q, Long categoriaId, String orden, Pageable pageable) {
        Sort sort = Sort.unsorted();
        if ("asc".equalsIgnoreCase(orden))
            sort = Sort.by(Sort.Direction.ASC, "precio_venta");
        else if ("desc".equalsIgnoreCase(orden))
            sort = Sort.by(Sort.Direction.DESC, "precio_venta");
        else if ("stock".equalsIgnoreCase(orden))
            sort = Sort.by(Sort.Direction.DESC, "stock");

        String nombre = (q == null) ? "" : q.trim();
        Pageable ordenado = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                sort);

        return productoRepository.buscarYFiltrar(nombre, categoriaId, ordenado);
    }
}
