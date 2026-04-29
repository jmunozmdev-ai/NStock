package com.nstock.nstock.entity;

import java.io.Serializable;
import java.util.Objects;

// Esta clase solo sirve para representar la llave doble (Producto + Sucursal)
public class InventarioId implements Serializable {

    private Integer producto;
    private Integer sucursal;

    public InventarioId() {
    }

    // Spring necesita estos dos métodos (equals y hashCode) para comparar las llaves correctamente
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventarioId that = (InventarioId) o;
        return Objects.equals(producto, that.producto) && Objects.equals(sucursal, that.sucursal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(producto, sucursal);
    }
}