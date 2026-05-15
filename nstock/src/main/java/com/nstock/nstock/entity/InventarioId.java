package com.nstock.nstock.entity;

import java.io.Serializable;
import java.util.Objects;

public class InventarioId implements Serializable {

    private Integer idProducto;
    private Integer idSucursal;

    // Constructor vacío (obligatorio para Spring Boot)
    public InventarioId() {}

    // Constructor con los dos parámetros (el que soluciona tu error rojo)
    public InventarioId(Integer idProducto, Integer idSucursal) {
        this.idProducto = idProducto;
        this.idSucursal = idSucursal;
    }

    // Getters y Setters
    public Integer getIdProducto() { return idProducto; }
    public void setIdProducto(Integer idProducto) { this.idProducto = idProducto; }
    public Integer getIdSucursal() { return idSucursal; }
    public void setIdSucursal(Integer idSucursal) { this.idSucursal = idSucursal; }

    // Métodos obligatorios para llaves compuestas en JPA
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventarioId that = (InventarioId) o;
        return Objects.equals(idProducto, that.idProducto) &&
               Objects.equals(idSucursal, that.idSucursal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProducto, idSucursal);
    }
}