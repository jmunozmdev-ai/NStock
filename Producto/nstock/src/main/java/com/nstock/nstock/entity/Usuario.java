package com.nstock.nstock.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    // Usamos String para el ENUM, pero en la base de datos se guarda como el tipo ENUM definido
    @Column(nullable = false)
    private String rol;

    // Aquí está la CORRELACIÓN con la llave foránea de MySQL:
    // @ManyToOne indica que muchos usuarios pueden estar en una sucursal.
    // @JoinColumn especifica la columna que une ambas tablas.
    @ManyToOne
    @JoinColumn(name = "id_sucursal")
    private Sucursal sucursal;

    public Usuario() {
    }

    // ==========================================
    // Getters y Setters
    // ==========================================
    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }
}