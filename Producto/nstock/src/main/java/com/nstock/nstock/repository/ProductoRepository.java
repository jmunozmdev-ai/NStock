package com.nstock.nstock.repository;

import com.nstock.nstock.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    
    // Optional maneja el caso donde el cajero escanee un código que no existe
    Optional<Producto> findByCodigoBarras(String codigoBarras);
}