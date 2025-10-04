package com.proyect.api_biblioteca.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad JPA que representa un préstamo de libro en la biblioteca
 */
@Entity
@Table(name = "prestamos")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Evita problemas de serialización con Hibernate
public class Prestamo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "El usuario es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnoreProperties("prestamos") // Evita referencias circulares
    private Usuario usuario;
    
    @NotNull(message = "El libro es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "libro_id", nullable = false)
    @JsonIgnoreProperties("prestamos") // Evita referencias circulares
    private Libro libro;
    
    @NotNull(message = "La fecha de préstamo es obligatoria")
    @Column(name = "fecha_prestamo", nullable = false)
    private LocalDate fechaPrestamo;
    
    @Column(name = "fecha_devolucion_esperada")
    private LocalDate fechaDevolucionEsperada;
    
    @Column(name = "fecha_devolucion_real")
    private LocalDate fechaDevolucionReal;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_prestamo")
    private EstadoPrestamo estado;
    
    @Column(name = "observaciones", length = 500)
    private String observaciones;
    
    @Column(name = "multa")
    private Double multa;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;
    
    // Constructores
    public Prestamo() {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaModificacion = LocalDateTime.now();
        this.estado = EstadoPrestamo.ACTIVO;
    }
    
    public Prestamo(Usuario usuario, Libro libro, LocalDate fechaPrestamo, LocalDate fechaDevolucionEsperada) {
        this();
        this.usuario = usuario;
        this.libro = libro;
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
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public Libro getLibro() {
        return libro;
    }
    
    public void setLibro(Libro libro) {
        this.libro = libro;
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
        this.fechaModificacion = LocalDateTime.now();
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
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }
    
    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
    
    // Métodos de utilidad
    public boolean isActivo() {
        return estado == EstadoPrestamo.ACTIVO;
    }
    
    public boolean isVencido() {
        return estado == EstadoPrestamo.ACTIVO && 
               LocalDate.now().isAfter(fechaDevolucionEsperada);
    }
    
    public boolean isDevuelto() {
        return estado == EstadoPrestamo.DEVUELTO;
    }
    
    public long getDiasTranscurridos() {
        if (fechaDevolucionReal != null) {
            return java.time.temporal.ChronoUnit.DAYS.between(fechaPrestamo, fechaDevolucionReal);
        }
        return java.time.temporal.ChronoUnit.DAYS.between(fechaPrestamo, LocalDate.now());
    }
    
    public long getDiasVencido() {
        if (isVencido()) {
            return java.time.temporal.ChronoUnit.DAYS.between(fechaDevolucionEsperada, LocalDate.now());
        }
        return 0;
    }
    
    @Override
    public String toString() {
        return "Prestamo{" +
                "id=" + id +
                ", usuario=" + (usuario != null ? usuario.getNombreCompleto() : "null") +
                ", libro=" + (libro != null ? libro.getTitulo() : "null") +
                ", fechaPrestamo=" + fechaPrestamo +
                ", estado=" + estado +
                '}';
    }
}
