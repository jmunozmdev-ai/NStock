package com.nstock.nstock.repository;

import com.nstock.nstock.entity.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// @Repository le avisa a Spring Boot que este archivo se encargará de hablar con la base de datos
@Repository
// Extendemos de JpaRepository. Entre los signos < > le pasamos dos cosas:
// 1. La Entidad que va a manejar (Sucursal)
// 2. El tipo de dato de su Llave Primaria (Integer)
public interface SucursalRepository extends JpaRepository<Sucursal, Integer> {
    
    // ¡Y listo! No necesitas escribir NADA MÁS aquí adentro.
    // Solo por hacer esto, Spring ya te regala métodos como:
    // findAll() -> Trae todas las sucursales
    // save() -> Guarda una sucursal nueva
    // deleteById() -> Borra una sucursal
}