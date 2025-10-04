package com.proyect.api_biblioteca.model.entity;

/**
 * Enum que representa los posibles estados de un préstamo
 */
public enum EstadoPrestamo {
    ACTIVO("Activo", "Préstamo en curso"),
    DEVUELTO("Devuelto", "Libro devuelto correctamente"),
    VENCIDO("Vencido", "Préstamo vencido sin devolver"),
    PERDIDO("Perdido", "Libro perdido durante el préstamo"),
    RENOVADO("Renovado", "Préstamo renovado"),
    CANCELADO("Cancelado", "Préstamo cancelado");
    
    private final String descripcion;
    private final String estado;
    
    EstadoPrestamo(String descripcion, String estado) {
        this.descripcion = descripcion;
        this.estado = estado;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public String getEstado() {
        return estado;
    }
}
