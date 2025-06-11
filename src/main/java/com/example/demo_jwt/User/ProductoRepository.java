package com.example.demo_jwt.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    // Buscar productos por nombre (contiene)
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    
    // Buscar productos por categoría
    List<Producto> findByCategoria(String categoria);
    
    // Buscar productos con stock bajo
    @Query("SELECT p FROM Producto p WHERE p.stock <= :stockMinimo")
    List<Producto> findProductosConStockBajo(Integer stockMinimo);
    
    // Buscar productos sin stock
    List<Producto> findByStock(Integer stock);
    
    // Contar productos por categoría
    @Query("SELECT COUNT(p) FROM Producto p WHERE p.categoria = :categoria")
    Long countByCategoria(String categoria);
}