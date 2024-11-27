package com.example.BorrarLoginJWT.Entity;

import jakarta.persistence.*;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    private String nombre;
    private String contrasenia;
    @Transient
    private String token;

    public Usuario(Long idUsuario, String nombre, String contrasenia, String token) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.contrasenia = contrasenia;
        this.token = token;
    }

    public Usuario() {
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
