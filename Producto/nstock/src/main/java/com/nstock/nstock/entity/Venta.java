package com.nstock.nstock.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta")
    private Integer idVenta;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    @Column(name = "total")
    private Integer total; // Unificado a Integer para la app móvil

    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "id_sucursal")
    private Integer idSucursal;

    @Column(name = "medio_pago")
    private String medioPago;

    // --- GETTERS ---
    public Integer getIdVenta() {
        return idVenta;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public Integer getTotal() {
        return total;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public String getMedioPago() {
        return medioPago;
    }

    // --- SETTERS ---
    public void setIdVenta(Integer idVenta) {
        this.idVenta = idVenta;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }
}
