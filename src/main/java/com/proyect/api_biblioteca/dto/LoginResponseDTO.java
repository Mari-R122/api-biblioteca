package com.proyect.api_biblioteca.dto;

import com.proyect.api_biblioteca.model.entity.TipoUsuario;

/**
 * DTO para respuestas de login
 */
public class LoginResponseDTO {
    
    private String token;
    private String email;
    private String nombre;
    private String apellido;
    private TipoUsuario tipoUsuario;
    private String mensaje;
    
    // Constructores
    public LoginResponseDTO() {}
    
    public LoginResponseDTO(String token, String email, String nombre, String apellido, TipoUsuario tipoUsuario) {
        this.token = token;
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoUsuario = tipoUsuario;
    }
    
    public LoginResponseDTO(String mensaje) {
        this.mensaje = mensaje;
    }
    
    // Getters y Setters
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getApellido() {
        return apellido;
    }
    
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }
    
    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    
    public String getMensaje() {
        return mensaje;
    }
    
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
}
