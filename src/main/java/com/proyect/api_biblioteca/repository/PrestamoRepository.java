package com.proyect.api_biblioteca.repository;

import com.proyect.api_biblioteca.model.entity.Prestamo;
import com.proyect.api_biblioteca.model.entity.EstadoPrestamo;
import com.proyect.api_biblioteca.model.entity.Usuario;
import com.proyect.api_biblioteca.model.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para la entidad Prestamo
 */
@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    
    /**
     * Busca préstamos por usuario
     * @param usuario el usuario
     * @return lista de préstamos del usuario
     */
    List<Prestamo> findByUsuario(Usuario usuario);
    
    /**
     * Busca préstamos por libro
     * @param libro el libro
     * @return lista de préstamos del libro
     */
    List<Prestamo> findByLibro(Libro libro);
    
    /**
     * Busca préstamos por estado
     * @param estado el estado del préstamo
     * @return lista de préstamos con el estado especificado
     */
    List<Prestamo> findByEstado(EstadoPrestamo estado);
    
    /**
     * Busca préstamos activos
     * @return lista de préstamos activos
     */
    @Query("SELECT p FROM Prestamo p WHERE p.estado = 'ACTIVO'")
    List<Prestamo> findPrestamosActivos();
    
    /**
     * Busca préstamos vencidos
     * @return lista de préstamos vencidos
     */
    @Query("SELECT p FROM Prestamo p WHERE p.estado = 'ACTIVO' AND p.fechaDevolucionEsperada < :fechaActual")
    List<Prestamo> findPrestamosVencidos(@Param("fechaActual") LocalDate fechaActual);
    
    /**
     * Busca préstamos por rango de fechas
     * @param fechaInicio fecha de inicio
     * @param fechaFin fecha de fin
     * @return lista de préstamos en el rango especificado
     */
    List<Prestamo> findByFechaPrestamoBetween(LocalDate fechaInicio, LocalDate fechaFin);
    
    /**
     * Verifica si un usuario tiene préstamos activos
     * @param usuario el usuario
     * @return true si tiene préstamos activos, false en caso contrario
     */
    @Query("SELECT COUNT(p) > 0 FROM Prestamo p WHERE p.usuario = :usuario AND p.estado = 'ACTIVO'")
    boolean hasPrestamosActivos(@Param("usuario") Usuario usuario);
    
    /**
     * Cuenta préstamos activos por usuario
     * @param usuario el usuario
     * @return número de préstamos activos del usuario
     */
    long countByUsuarioAndEstado(Usuario usuario, EstadoPrestamo estado);
    
    /**
     * Busca préstamos que vencen en una fecha específica
     * @param fecha la fecha de vencimiento
     * @return lista de préstamos que vencen en esa fecha
     */
    List<Prestamo> findByFechaDevolucionEsperada(LocalDate fecha);
    
    /**
     * Busca préstamos que vencen pronto (en los próximos N días)
     * @param fechaLimite fecha límite
     * @return lista de préstamos que vencen pronto
     */
    @Query("SELECT p FROM Prestamo p WHERE p.estado = 'ACTIVO' AND p.fechaDevolucionEsperada BETWEEN :hoy AND :fechaLimite")
    List<Prestamo> findPrestamosVencenPronto(@Param("hoy") LocalDate hoy, @Param("fechaLimite") LocalDate fechaLimite);
    
    /**
     * Cuenta préstamos por estado
     * @param estado el estado del préstamo
     * @return número de préstamos con el estado especificado
     */
    long countByEstado(EstadoPrestamo estado);
    
    /**
     * Busca el último préstamo de un libro por un usuario
     * @param libro el libro
     * @param usuario el usuario
     * @return Optional con el último préstamo si existe
     */
    @Query("SELECT p FROM Prestamo p WHERE p.libro = :libro AND p.usuario = :usuario ORDER BY p.fechaPrestamo DESC")
    Optional<Prestamo> findLastPrestamoByLibroAndUsuario(@Param("libro") Libro libro, @Param("usuario") Usuario usuario);
}
