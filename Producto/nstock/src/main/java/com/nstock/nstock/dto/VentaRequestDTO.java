package com.nstock.nstock.dto;

import java.util.List;

public class VentaRequestDTO {

    private Integer idSucursal;
    private Integer idUsuario;
    private String medioPago;

    // Aquí recibimos la lista de productos que armamos en el paso anterior
    private List<DetalleVentaDTO> detalles;

    // Getters
    public Integer getIdSucursal() {
        return idSucursal;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public List<DetalleVentaDTO> getDetalles() {
        return detalles;
    }

    public String getMedioPago() {
        return medioPago;
    }

    // Setters
    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setDetalles(List<DetalleVentaDTO> detalles) {
        this.detalles = detalles;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }

}
