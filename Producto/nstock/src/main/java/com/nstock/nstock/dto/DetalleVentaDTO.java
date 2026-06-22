package com.nstock.nstock.dto;

public class DetalleVentaDTO {
    
    private Integer idProducto;
    private Integer cantidad;

    // Getters
    public Integer getIdProducto() { return idProducto; }
    public Integer getCantidad() { return cantidad; }

    // Setters
    public void setIdProducto(Integer idProducto) { this.idProducto = idProducto; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
}