package com.nstock.nstock.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Productos") // Respetando la mayúscula de tu script SQL
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer idProducto;

    @Column(name = "codigo_barras", unique = true)
    private String codigoBarras;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "precio")
    private Integer precio; // Forzamos el formato entero para la app móvil

    @Column(name = "categoria")
    private String categoria;

    // Genera aquí tus Getters y Setters (Clic derecho -> Source Action -> Generate Getters and Setters)
    
    public Integer getIdProducto() { return idProducto; }
    public void setIdProducto(Integer idProducto) { this.idProducto = idProducto; }
    public String getCodigoBarras() { return codigoBarras; }
    public void setCodigoBarras(String codigoBarras) { this.codigoBarras = codigoBarras; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Integer getPrecio() { return precio; }
    public void setPrecio(Integer precio) { this.precio = precio; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}