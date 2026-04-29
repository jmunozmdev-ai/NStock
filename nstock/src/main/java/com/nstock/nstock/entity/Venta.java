package com.nstock.nstock.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta")
    private Integer idVenta;

    // LocalDateTime es la clase moderna de Java para manejar Fechas y Horas
    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    // Relación: Qué cajero hizo la venta
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    // Relación: En qué local se hizo
    @ManyToOne
    @JoinColumn(name = "id_sucursal")
    private Sucursal sucursal;

    public Venta() {
    }

    // ==========================================
    // Getters y Setters
    // ==========================================
    public Integer getIdVenta() { return idVenta; }
    public void setIdVenta(Integer idVenta) { this.idVenta = idVenta; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Sucursal getSucursal() { return sucursal; }
    public void setSucursal(Sucursal sucursal) { this.sucursal = sucursal; }
}