package com.proyect.api_biblioteca.model.entity;

/**
 * Enum que representa los tipos de usuario en el sistema de biblioteca
 */
public enum TipoUsuario {
    BIBLIOTECARIO("Bibliotecario", "Tiene acceso completo al sistema"),
    USUARIO("Usuario Regular", "Puede realizar préstamos y consultas");
    
    private final String descripcion;
    private final String permisos;
    
    TipoUsuario(String descripcion, String permisos) {
        this.descripcion = descripcion;
        this.permisos = permisos;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public String getPermisos() {
        return permisos;
    }
}
