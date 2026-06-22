package com.nstock.nstock.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Detalle_Ventas") // Respetando la mayúscula de tu diagrama SQL
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Integer idDetalle;

    @Column(name = "id_venta")
    private Integer idVenta;

    @Column(name = "id_producto")
    private Integer idProducto;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "precio_unitario")
    private Integer precioUnitario;

    @Column(name = "subtotal")
    private Integer subtotal;

    // Getters
    public Integer getIdDetalle() { return idDetalle; }
    public Integer getIdVenta() { return idVenta; }
    public Integer getIdProducto() { return idProducto; }
    public Integer getCantidad() { return cantidad; }
    public Integer getPrecioUnitario() { return precioUnitario; }
    public Integer getSubtotal() { return subtotal; }

    // Setters
    public void setIdDetalle(Integer idDetalle) { this.idDetalle = idDetalle; }
    public void setIdVenta(Integer idVenta) { this.idVenta = idVenta; }
    public void setIdProducto(Integer idProducto) { this.idProducto = idProducto; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public void setPrecioUnitario(Integer precioUnitario) { this.precioUnitario = precioUnitario; }
    public void setSubtotal(Integer subtotal) { this.subtotal = subtotal; }
}