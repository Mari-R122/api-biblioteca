package com.proyect.api_biblioteca.model.entity;

/**
 * Enum que representa los posibles estados de un libro en la biblioteca
 */
public enum EstadoLibro {
    DISPONIBLE("Disponible para préstamo"),
    PRESTADO("Prestado a un usuario"),
    EN_REPARACION("En reparación"),
    PERDIDO("Perdido"),
    RETIRADO("Retirado del sistema");
    
    private final String descripcion;
    
    EstadoLibro(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
}
