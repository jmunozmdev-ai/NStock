package com.nstock.nstock.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Inventario")
// Usamos @IdClass para decirle a Spring que la llave primaria está compuesta por dos campos
@IdClass(InventarioId.class)
public class Inventario {

    // Llave primaria 1 y Foránea hacia Producto
    @Id
    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;

    // Llave primaria 2 y Foránea hacia Sucursal
    @Id
    @ManyToOne
    @JoinColumn(name = "id_sucursal")
    private Sucursal sucursal;

    @Column(name = "stock_actual", columnDefinition = "int default 0")
    private Integer stockActual;

    public Inventario() {
    }

    // ==========================================
    // Getters y Setters
    // ==========================================
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Integer getStockActual() {
        return stockActual;
    }

    public void setStockActual(Integer stockActual) {
        this.stockActual = stockActual;
    }
}