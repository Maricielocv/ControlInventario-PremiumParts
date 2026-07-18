package com.proyecto.sistema.inventario.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyecto.sistema.inventario.entities.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

        @Query("""
                        select p.nombre from Producto p
                        where lower(p.nombre) like lower(concat('%', :q, '%'))
                        order by p.nombre asc
                                """)
        List<String> sugerirNombres(@Param("q") String q, Pageable pagable);

        @Query("select p from Producto p order by rand()")
        List<Producto> listadoAleatorio(Pageable pagable);

        List<Producto> findByNombre(String nombre);

        List<Producto> findByStockLessThan(Integer stock);

        List<Producto> findByCategoriaId(Long categoriaId);

        List<Producto> findByNombreAndCategoriaId(String nombre, Long categoriaId);

        @Query("SELECT p FROM Producto p WHERE " +
                        "(:nombre IS NULL OR :nombre = '' OR p.nombre = :nombre) AND " +
                        "(:categoriaId IS NULL OR p.categoria.id = :categoriaId)")
        // se cambio la lista por el page para poder paginar
        Page<Producto> buscarYFiltrar(@Param("nombre") String nombre, @Param("categoriaId") Long categoriaId,
                        Pageable pageable);

        // top 10 productos mas vendidos
        @Query("SELECT p FROM Producto p ORDER BY p.veces_vendido DESC")
        List<Producto> findTop10MasVendidos(Pageable pageable);

        @Query("SELECT p.categoria.nombre, COUNT(p) FROM Producto p GROUP BY p.categoria.nombre")
        List<Object[]> productosPorCategoria();

}
