package com.nstock.nstock.entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "Movimientos_Inventario")
public class MovimientoInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento")
    private Integer idMovimiento;

    @Column(name = "id_producto")
    private Integer idProducto;

    @Column(name = "id_sucursal")
    private Integer idSucursal;

    @Column(name = "tipo_movimiento")
    private String tipoMovimiento; // Ej: "VENTA", "INGRESO", "AJUSTE"

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    @Column(name = "descripcion")
    private String descripcion;

    // Importante: Asegúrate de importar el Enum en la parte de arriba si está en otra carpeta


    @Enumerated(EnumType.STRING)
    @Column(name = "motivo_merma")
    private MotivoMerma motivoMerma;

    @Column(name = "comentario", length = 255)
    private String comentario;


  // --- Getters y Setters ---
    public Integer getIdMovimiento() { return idMovimiento; }
    public void setIdMovimiento(Integer idMovimiento) { this.idMovimiento = idMovimiento; }
    public Integer getIdProducto() { return idProducto; }
    public void setIdProducto(Integer idProducto) { this.idProducto = idProducto; }
    public Integer getIdSucursal() { return idSucursal; }
    public void setIdSucursal(Integer idSucursal) { this.idSucursal = idSucursal; }
    public String getTipoMovimiento() { return tipoMovimiento; }
    public void setTipoMovimiento(String tipoMovimiento) { this.tipoMovimiento = tipoMovimiento; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public MotivoMerma getMotivoMerma() { return motivoMerma;}
    public void setMotivoMerma(MotivoMerma motivoMerma) {this.motivoMerma = motivoMerma;}
    public String getComentario() {return comentario; }
    public void setComentario(String comentario) {this.comentario = comentario;}
}