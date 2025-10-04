package com.proyect.api_biblioteca.dto;

import com.proyect.api_biblioteca.model.entity.EstadoUsuario;
import com.proyect.api_biblioteca.model.entity.TipoUsuario;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

/**
 * DTO para transferir datos de usuario
 */
public class UsuarioDTO {
    
    private Long id;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;
    
    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100, message = "El apellido no puede exceder 100 caracteres")
    private String apellido;
    
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    @Size(max = 255, message = "El email no puede exceder 255 caracteres")
    private String email;
    
    @Pattern(regexp = "^[0-9]{8,15}$", message = "El teléfono debe contener entre 8 y 15 dígitos")
    private String telefono;
    
    @Size(max = 500, message = "La dirección no puede exceder 500 caracteres")
    private String direccion;
    
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    private LocalDate fechaNacimiento;
    
    private TipoUsuario tipoUsuario;
    
    private EstadoUsuario estado;
    
    // Constructores
    public UsuarioDTO() {}
    
    public UsuarioDTO(String nombre, String apellido, String email, LocalDate fechaNacimiento, TipoUsuario tipoUsuario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.tipoUsuario = tipoUsuario;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getDireccion() {
        return direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }
    
    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    
    public EstadoUsuario getEstado() {
        return estado;
    }
    
    public void setEstado(EstadoUsuario estado) {
        this.estado = estado;
    }
    
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
}
