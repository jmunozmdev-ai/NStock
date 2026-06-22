package com.nstock.nstock.entity;

import jakarta.persistence.*;

// @Entity le dice a Spring que esta clase representa una tabla en la Base de Datos
@Entity
// @Table especifica el nombre exacto de la tabla en MySQL para evitar confusiones
@Table(name = "Sucursales")
public class Sucursal {

    // @Id indica que este campo es la Llave Primaria (Primary Key)
    @Id
    // @GeneratedValue indica que es AUTO_INCREMENT (la base de datos asigna el número)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sucursal")
    private Integer idSucursal;

    // @Column mapea esta variable con la columna "nombre" de tu tabla
    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 200)
    private String direccion;

    // ==========================================
    // Constructores vacíos exigidos por Spring
    // ==========================================
    public Sucursal() {
    }

    // ==========================================
    // Getters y Setters (Para leer y modificar los datos)
    // ==========================================
    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}