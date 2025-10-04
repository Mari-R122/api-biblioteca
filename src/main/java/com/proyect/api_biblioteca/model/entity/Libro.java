package com.proyect.api_biblioteca.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Entidad JPA que representa un libro en la biblioteca
 */
@Entity
@Table(name = "libros")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Evita problemas de serialización con Hibernate
public class Libro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El título es obligatorio")
    @Size(max = 255, message = "El título no puede exceder 255 caracteres")
    @Column(nullable = false)
    private String titulo;
    
    @NotBlank(message = "El autor es obligatorio")
    @Size(max = 255, message = "El nombre del autor no puede exceder 255 caracteres")
    @Column(nullable = false)
    private String autor;
    
    @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
    @Column(length = 1000)
    private String descripcion;
    
    @NotNull(message = "El año de publicación es obligatorio")
    @Min(value = 1000, message = "El año debe ser válido")
    @Max(value = 2024, message = "El año no puede ser futuro")
    @Column(name = "anio_publicacion", nullable = false)
    private Integer anioPublicacion;
    
    @NotNull(message = "El ISBN es obligatorio")
    @Pattern(regexp = "^[0-9]{13}$", message = "El ISBN debe tener 13 dígitos")
    @Column(unique = true, nullable = false, length = 13)
    private String isbn;
    
    @Min(value = 1, message = "El número de páginas debe ser al menos 1")
    @Column(name = "numero_paginas")
    private Integer numeroPaginas;
    
    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_libro")
    private EstadoLibro estado;
    
    // Relación con préstamos
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore // Evita referencias circulares en JSON
    private List<Prestamo> prestamos;
    
    // Constructores
    public Libro() {
        this.fechaIngreso = LocalDate.now();
        this.estado = EstadoLibro.DISPONIBLE;
    }
    
    public Libro(String titulo, String autor, Integer anioPublicacion, String isbn) {
        this();
        this.titulo = titulo;
        this.autor = autor;
        this.anioPublicacion = anioPublicacion;
        this.isbn = isbn;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getAutor() {
        return autor;
    }
    
    public void setAutor(String autor) {
        this.autor = autor;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public Integer getAnioPublicacion() {
        return anioPublicacion;
    }
    
    public void setAnioPublicacion(Integer anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public Integer getNumeroPaginas() {
        return numeroPaginas;
    }
    
    public void setNumeroPaginas(Integer numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }
    
    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }
    
    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
    
    public EstadoLibro getEstado() {
        return estado;
    }
    
    public void setEstado(EstadoLibro estado) {
        this.estado = estado;
    }
    
    public List<Prestamo> getPrestamos() {
        return prestamos;
    }
    
    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }
    
    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", anioPublicacion=" + anioPublicacion +
                ", isbn='" + isbn + '\'' +
                ", estado=" + estado +
                '}';
    }
}
