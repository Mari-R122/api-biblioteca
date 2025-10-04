package com.proyect.api_biblioteca.repository;

import com.proyect.api_biblioteca.model.entity.Libro;
import com.proyect.api_biblioteca.model.entity.EstadoLibro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para la entidad Libro
 * Extiende JpaRepository para operaciones CRUD básicas
 */
@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    
    /**
     * Busca libros por título (ignorando mayúsculas/minúsculas)
     * @param titulo el título a buscar
     * @return lista de libros que coinciden con el título
     */
    List<Libro> findByTituloContainingIgnoreCase(String titulo);
    
    /**
     * Busca libros por autor (ignorando mayúsculas/minúsculas)
     * @param autor el autor a buscar
     * @return lista de libros del autor especificado
     */
    List<Libro> findByAutorContainingIgnoreCase(String autor);
    
    /**
     * Busca un libro por ISBN
     * @param isbn el ISBN del libro
     * @return Optional con el libro si existe
     */
    Optional<Libro> findByIsbn(String isbn);
    
    /**
     * Busca libros por estado
     * @param estado el estado del libro
     * @return lista de libros con el estado especificado
     */
    List<Libro> findByEstado(EstadoLibro estado);
    
    /**
     * Busca libros por año de publicación
     * @param anioPublicacion el año de publicación
     * @return lista de libros publicados en el año especificado
     */
    List<Libro> findByAnioPublicacion(Integer anioPublicacion);
    
    /**
     * Busca libros por rango de años de publicación
     * @param anioInicio año de inicio del rango
     * @param anioFin año de fin del rango
     * @return lista de libros publicados en el rango especificado
     */
    List<Libro> findByAnioPublicacionBetween(Integer anioInicio, Integer anioFin);
    
    /**
     * Busca libros disponibles para préstamo
     * @return lista de libros con estado DISPONIBLE
     */
    @Query("SELECT l FROM Libro l WHERE l.estado = 'DISPONIBLE'")
    List<Libro> findLibrosDisponibles();
    
    /**
     * Busca libros prestados
     * @return lista de libros con estado PRESTADO
     */
    @Query("SELECT l FROM Libro l WHERE l.estado = 'PRESTADO'")
    List<Libro> findLibrosPrestados();
    
    /**
     * Cuenta libros por estado
     * @param estado el estado del libro
     * @return número de libros con el estado especificado
     */
    long countByEstado(EstadoLibro estado);
    
    /**
     * Verifica si existe un libro con el ISBN especificado
     * @param isbn el ISBN a verificar
     * @return true si existe, false en caso contrario
     */
    boolean existsByIsbn(String isbn);
    
    /**
     * Busca libros por múltiples criterios
     * @param titulo título del libro (opcional)
     * @param autor autor del libro (opcional)
     * @param estado estado del libro (opcional)
     * @return lista de libros que coinciden con los criterios
     */
    @Query("SELECT l FROM Libro l WHERE " +
           "(:titulo IS NULL OR LOWER(l.titulo) LIKE LOWER(CONCAT('%', :titulo, '%'))) AND " +
           "(:autor IS NULL OR LOWER(l.autor) LIKE LOWER(CONCAT('%', :autor, '%'))) AND " +
           "(:estado IS NULL OR l.estado = :estado)")
    List<Libro> findByCriterios(@Param("titulo") String titulo, 
                               @Param("autor") String autor, 
                               @Param("estado") EstadoLibro estado);
}
