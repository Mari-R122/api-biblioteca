package com.proyect.api_biblioteca.model.entity;

/**
 * Enum que representa los posibles estados de un usuario en el sistema
 */
public enum EstadoUsuario {
    ACTIVO("Activo", "Usuario activo en el sistema"),
    INACTIVO("Inactivo", "Usuario temporalmente deshabilitado"),
    SUSPENDIDO("Suspendido", "Usuario suspendido por violaciones"),
    BLOQUEADO("Bloqueado", "Usuario bloqueado permanentemente");
    
    private final String descripcion;
    private final String motivo;
    
    EstadoUsuario(String descripcion, String motivo) {
        this.descripcion = descripcion;
        this.motivo = motivo;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public String getMotivo() {
        return motivo;
    }
}
