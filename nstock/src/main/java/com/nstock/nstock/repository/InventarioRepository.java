package com.nstock.nstock.repository;

import com.nstock.nstock.entity.Inventario;
import com.nstock.nstock.entity.InventarioId;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, InventarioId> {
    // Busca directamente por el campo idSucursal de tu entidad
    List<Inventario> findByIdSucursal(Integer idSucursal);


}