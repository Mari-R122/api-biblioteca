package com.proyect.api_biblioteca.dto;

import com.proyect.api_biblioteca.model.entity.EstadoPrestamo;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * DTO para transferir datos de préstamo
 */
public class PrestamoDTO {
    
    private Long id;
    
    @NotNull(message = "El ID del usuario es obligatorio")
    private Long usuarioId;
    
    @NotNull(message = "El ID del libro es obligatorio")
    private Long libroId;
    
    @NotNull(message = "La fecha de préstamo es obligatoria")
    private LocalDate fechaPrestamo;
    
    private LocalDate fechaDevolucionEsperada;
    
    private LocalDate fechaDevolucionReal;
    
    private EstadoPrestamo estado;
    
    private String observaciones;
    
    private Double multa;
    
    // Campos adicionales para mostrar información relacionada
    private String nombreUsuario;
    private String tituloLibro;
    private String autorLibro;
    
    // Constructores
    public PrestamoDTO() {}
    
    public PrestamoDTO(Long usuarioId, Long libroId, LocalDate fechaPrestamo, LocalDate fechaDevolucionEsperada) {
        this.usuarioId = usuarioId;
        this.libroId = libroId;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getUsuarioId() {
        return usuarioId;
    }
    
    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
    
    public Long getLibroId() {
        return libroId;
    }
    
    public void setLibroId(Long libroId) {
        this.libroId = libroId;
    }
    
    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }
    
    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }
    
    public LocalDate getFechaDevolucionEsperada() {
        return fechaDevolucionEsperada;
    }
    
    public void setFechaDevolucionEsperada(LocalDate fechaDevolucionEsperada) {
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
    }
    
    public LocalDate getFechaDevolucionReal() {
        return fechaDevolucionReal;
    }
    
    public void setFechaDevolucionReal(LocalDate fechaDevolucionReal) {
        this.fechaDevolucionReal = fechaDevolucionReal;
    }
    
    public EstadoPrestamo getEstado() {
        return estado;
    }
    
    public void setEstado(EstadoPrestamo estado) {
        this.estado = estado;
    }
    
    public String getObservaciones() {
        return observaciones;
    }
    
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public Double getMulta() {
        return multa;
    }
    
    public void setMulta(Double multa) {
        this.multa = multa;
    }
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
    public String getTituloLibro() {
        return tituloLibro;
    }
    
    public void setTituloLibro(String tituloLibro) {
        this.tituloLibro = tituloLibro;
    }
    
    public String getAutorLibro() {
        return autorLibro;
    }
    
    public void setAutorLibro(String autorLibro) {
        this.autorLibro = autorLibro;
    }
}
