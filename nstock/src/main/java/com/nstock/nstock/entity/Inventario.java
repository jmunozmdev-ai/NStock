package com.nstock.nstock.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Inventario")
@IdClass(InventarioId.class) // Aquí le decimos que use la llave compuesta
public class Inventario {

    // Debe llamarse EXACTAMENTE igual que en InventarioId.java
    @Id
    @Column(name = "id_producto")
    private Integer idProducto; 

    // Debe llamarse EXACTAMENTE igual que en InventarioId.java
    @Id
    @Column(name = "id_sucursal")
    private Integer idSucursal;

    @Column(name = "stock_actual")
    private Integer stockActual;

    // --- GETTERS ---
    public Integer getIdProducto() { return idProducto; }
    public Integer getIdSucursal() { return idSucursal; }
    public Integer getStockActual() { return stockActual; }

    // --- SETTERS ---
    public void setIdProducto(Integer idProducto) { this.idProducto = idProducto; }
    public void setIdSucursal(Integer idSucursal) { this.idSucursal = idSucursal; }
    public void setStockActual(Integer stockActual) { this.stockActual = stockActual; }
}