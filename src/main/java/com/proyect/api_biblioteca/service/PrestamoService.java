package com.proyect.api_biblioteca.service;

import com.proyect.api_biblioteca.model.entity.Prestamo;
import com.proyect.api_biblioteca.model.entity.Usuario;
import com.proyect.api_biblioteca.model.entity.Libro;
import com.proyect.api_biblioteca.model.entity.EstadoPrestamo;
import com.proyect.api_biblioteca.model.entity.EstadoLibro;
import com.proyect.api_biblioteca.repository.PrestamoRepository;
import com.proyect.api_biblioteca.repository.UsuarioRepository;
import com.proyect.api_biblioteca.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Servicio que maneja la lógica de negocio para la entidad Prestamo
 */
@Service
@Transactional
public class PrestamoService {
    
    @Autowired
    private PrestamoRepository prestamoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private LibroRepository libroRepository;
    
    // Configuración de préstamos
    private static final int DIAS_PRESTAMO = 15; // Días de duración del préstamo
    private static final int MAX_PRESTAMOS_USUARIO = 3; // Máximo de préstamos por usuario
    private static final double MULTA_POR_DIA = 1.0; // Multa por día de retraso
    
    /**
     * Obtiene todos los préstamos
     * @return lista de todos los préstamos
     */
    @Transactional(readOnly = true)
    public List<Prestamo> findAll() {
        return prestamoRepository.findAll();
    }
    
    /**
     * Busca un préstamo por su ID
     * @param id el ID del préstamo
     * @return Optional con el préstamo si existe
     */
    @Transactional(readOnly = true)
    public Optional<Prestamo> findById(Long id) {
        return prestamoRepository.findById(id);
    }
    
    /**
     * Crea un nuevo préstamo
     * @param usuarioId el ID del usuario
     * @param libroId el ID del libro
     * @return el préstamo creado
     * @throws IllegalArgumentException si hay problemas con la validación
     */
    public Prestamo crearPrestamo(Long usuarioId, Long libroId) {
        // Validar usuario
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("No existe un usuario con el ID: " + usuarioId));
        
        if (!usuario.isActivo()) {
            throw new IllegalArgumentException("El usuario no está activo");
        }
        
        // Validar libro
        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new IllegalArgumentException("No existe un libro con el ID: " + libroId));
        
        if (libro.getEstado() != EstadoLibro.DISPONIBLE) {
            throw new IllegalArgumentException("El libro no está disponible para préstamo");
        }
        
        // Verificar límite de préstamos del usuario
        long prestamosActivos = prestamoRepository.countByUsuarioAndEstado(usuario, EstadoPrestamo.ACTIVO);
        if (prestamosActivos >= MAX_PRESTAMOS_USUARIO) {
            throw new IllegalArgumentException("El usuario ha alcanzado el límite máximo de préstamos activos (" + MAX_PRESTAMOS_USUARIO + ")");
        }
        
        // Crear el préstamo
        LocalDate fechaPrestamo = LocalDate.now();
        LocalDate fechaDevolucionEsperada = fechaPrestamo.plusDays(DIAS_PRESTAMO);
        
        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(fechaPrestamo);
        prestamo.setFechaDevolucionEsperada(fechaDevolucionEsperada);
        prestamo.setEstado(EstadoPrestamo.ACTIVO);
        
        // Actualizar estado del libro
        libro.setEstado(EstadoLibro.PRESTADO);
        libroRepository.save(libro);
        
        return prestamoRepository.save(prestamo);
    }
    
    /**
     * Devuelve un libro
     * @param prestamoId el ID del préstamo
     * @param observaciones observaciones de la devolución
     * @return el préstamo actualizado
     * @throws IllegalArgumentException si el préstamo no existe o ya está devuelto
     */
    public Prestamo devolverLibro(Long prestamoId, String observaciones) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new IllegalArgumentException("No existe un préstamo con el ID: " + prestamoId));
        
        if (prestamo.getEstado() != EstadoPrestamo.ACTIVO) {
            throw new IllegalArgumentException("El préstamo no está activo");
        }
        
        // Actualizar el préstamo
        prestamo.setFechaDevolucionReal(LocalDate.now());
        prestamo.setEstado(EstadoPrestamo.DEVUELTO);
        prestamo.setObservaciones(observaciones);
        
        // Calcular multa si hay retraso
        if (prestamo.isVencido()) {
            long diasVencido = prestamo.getDiasVencido();
            double multa = diasVencido * MULTA_POR_DIA;
            prestamo.setMulta(multa);
        }
        
        // Actualizar estado del libro
        Libro libro = prestamo.getLibro();
        libro.setEstado(EstadoLibro.DISPONIBLE);
        libroRepository.save(libro);
        
        return prestamoRepository.save(prestamo);
    }
    
    /**
     * Renueva un préstamo
     * @param prestamoId el ID del préstamo
     * @return el préstamo renovado
     * @throws IllegalArgumentException si el préstamo no se puede renovar
     */
    public Prestamo renovarPrestamo(Long prestamoId) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new IllegalArgumentException("No existe un préstamo con el ID: " + prestamoId));
        
        if (prestamo.getEstado() != EstadoPrestamo.ACTIVO) {
            throw new IllegalArgumentException("Solo se pueden renovar préstamos activos");
        }
        
        if (prestamo.isVencido()) {
            throw new IllegalArgumentException("No se puede renovar un préstamo vencido");
        }
        
        // Extender la fecha de devolución
        LocalDate nuevaFechaDevolucion = prestamo.getFechaDevolucionEsperada().plusDays(DIAS_PRESTAMO);
        prestamo.setFechaDevolucionEsperada(nuevaFechaDevolucion);
        prestamo.setEstado(EstadoPrestamo.RENOVADO);
        
        return prestamoRepository.save(prestamo);
    }
    
    /**
     * Busca préstamos por usuario
     * @param usuarioId el ID del usuario
     * @return lista de préstamos del usuario
     */
    @Transactional(readOnly = true)
    public List<Prestamo> findByUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("No existe un usuario con el ID: " + usuarioId));
        return prestamoRepository.findByUsuario(usuario);
    }
    
    /**
     * Busca préstamos por libro
     * @param libroId el ID del libro
     * @return lista de préstamos del libro
     */
    @Transactional(readOnly = true)
    public List<Prestamo> findByLibro(Long libroId) {
        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new IllegalArgumentException("No existe un libro con el ID: " + libroId));
        return prestamoRepository.findByLibro(libro);
    }
    
    /**
     * Busca préstamos por estado
     * @param estado el estado del préstamo
     * @return lista de préstamos con el estado especificado
     */
    @Transactional(readOnly = true)
    public List<Prestamo> findByEstado(EstadoPrestamo estado) {
        return prestamoRepository.findByEstado(estado);
    }
    
    /**
     * Obtiene préstamos activos
     * @return lista de préstamos activos
     */
    @Transactional(readOnly = true)
    public List<Prestamo> findPrestamosActivos() {
        return prestamoRepository.findPrestamosActivos();
    }
    
    /**
     * Obtiene préstamos vencidos
     * @return lista de préstamos vencidos
     */
    @Transactional(readOnly = true)
    public List<Prestamo> findPrestamosVencidos() {
        return prestamoRepository.findPrestamosVencidos(LocalDate.now());
    }
    
    /**
     * Obtiene préstamos que vencen pronto (en los próximos 3 días)
     * @return lista de préstamos que vencen pronto
     */
    @Transactional(readOnly = true)
    public List<Prestamo> findPrestamosVencenPronto() {
        LocalDate hoy = LocalDate.now();
        LocalDate fechaLimite = hoy.plusDays(3);
        return prestamoRepository.findPrestamosVencenPronto(hoy, fechaLimite);
    }
    
    /**
     * Elimina un préstamo
     * @param id el ID del préstamo a eliminar
     * @throws IllegalArgumentException si el préstamo no existe
     */
    public void deleteById(Long id) {
        if (!prestamoRepository.existsById(id)) {
            throw new IllegalArgumentException("No existe un préstamo con el ID: " + id);
        }
        prestamoRepository.deleteById(id);
    }
    
    /**
     * Cuenta préstamos por estado
     * @param estado el estado del préstamo
     * @return número de préstamos con el estado especificado
     */
    @Transactional(readOnly = true)
    public long countByEstado(EstadoPrestamo estado) {
        return prestamoRepository.countByEstado(estado);
    }
    
    /**
     * Verifica si un préstamo existe por su ID
     * @param id el ID del préstamo
     * @return true si existe, false en caso contrario
     */
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return prestamoRepository.existsById(id);
    }
    
    /**
     * Obtiene estadísticas de préstamos
     * @return mapa con estadísticas
     */
    @Transactional(readOnly = true)
    public java.util.Map<String, Object> getEstadisticas() {
        long totalActivos = countByEstado(EstadoPrestamo.ACTIVO);
        long totalDevueltos = countByEstado(EstadoPrestamo.DEVUELTO);
        long totalVencidos = countByEstado(EstadoPrestamo.VENCIDO);
        long totalRenovados = countByEstado(EstadoPrestamo.RENOVADO);
        
        java.util.Map<String, Object> estadisticas = new java.util.HashMap<>();
        estadisticas.put("totalActivos", totalActivos);
        estadisticas.put("totalDevueltos", totalDevueltos);
        estadisticas.put("totalVencidos", totalVencidos);
        estadisticas.put("totalRenovados", totalRenovados);
        estadisticas.put("total", totalActivos + totalDevueltos + totalVencidos + totalRenovados);
        
        return estadisticas;
    }
}
