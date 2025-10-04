package com.proyect.api_biblioteca.service;

import com.proyect.api_biblioteca.model.entity.Libro;
import com.proyect.api_biblioteca.model.entity.EstadoLibro;
import com.proyect.api_biblioteca.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que maneja la lógica de negocio para la entidad Libro
 */
@Service
@Transactional
public class LibroService {
    
    @Autowired
    private LibroRepository libroRepository;
    
    /**
     * Obtiene todos los libros
     * @return lista de todos los libros
     */
    @Transactional(readOnly = true)
    public List<Libro> findAll() {
        return libroRepository.findAll();
    }
    
    /**
     * Busca un libro por su ID
     * @param id el ID del libro
     * @return Optional con el libro si existe
     */
    @Transactional(readOnly = true)
    public Optional<Libro> findById(Long id) {
        return libroRepository.findById(id);
    }
    
    /**
     * Guarda un nuevo libro
     * @param libro el libro a guardar
     * @return el libro guardado
     * @throws IllegalArgumentException si el ISBN ya existe
     */
    public Libro save(Libro libro) {
        // Verificar si el ISBN ya existe
        if (libro.getId() == null && libroRepository.existsByIsbn(libro.getIsbn())) {
            throw new IllegalArgumentException("Ya existe un libro con el ISBN: " + libro.getIsbn());
        }
        
        return libroRepository.save(libro);
    }
    
    /**
     * Actualiza un libro existente
     * @param id el ID del libro a actualizar
     * @param libro los nuevos datos del libro
     * @return el libro actualizado
     * @throws IllegalArgumentException si el libro no existe o el ISBN ya está en uso
     */
    public Libro update(Long id, Libro libro) {
        if (!libroRepository.existsById(id)) {
            throw new IllegalArgumentException("No existe un libro con el ID: " + id);
        }
        
        // Verificar si el ISBN ya existe en otro libro
        Optional<Libro> libroExistente = libroRepository.findByIsbn(libro.getIsbn());
        if (libroExistente.isPresent() && !libroExistente.get().getId().equals(id)) {
            throw new IllegalArgumentException("Ya existe otro libro con el ISBN: " + libro.getIsbn());
        }
        
        libro.setId(id);
        return libroRepository.save(libro);
    }
    
    /**
     * Elimina un libro por su ID
     * @param id el ID del libro a eliminar
     * @throws IllegalArgumentException si el libro no existe
     */
    public void deleteById(Long id) {
        if (!libroRepository.existsById(id)) {
            throw new IllegalArgumentException("No existe un libro con el ID: " + id);
        }
        libroRepository.deleteById(id);
    }
    
    /**
     * Busca libros por título
     * @param titulo el título a buscar
     * @return lista de libros que coinciden con el título
     */
    @Transactional(readOnly = true)
    public List<Libro> findByTitulo(String titulo) {
        return libroRepository.findByTituloContainingIgnoreCase(titulo);
    }
    
    /**
     * Busca libros por autor
     * @param autor el autor a buscar
     * @return lista de libros del autor especificado
     */
    @Transactional(readOnly = true)
    public List<Libro> findByAutor(String autor) {
        return libroRepository.findByAutorContainingIgnoreCase(autor);
    }
    
    /**
     * Busca un libro por ISBN
     * @param isbn el ISBN del libro
     * @return Optional con el libro si existe
     */
    @Transactional(readOnly = true)
    public Optional<Libro> findByIsbn(String isbn) {
        return libroRepository.findByIsbn(isbn);
    }
    
    /**
     * Busca libros por estado
     * @param estado el estado del libro
     * @return lista de libros con el estado especificado
     */
    @Transactional(readOnly = true)
    public List<Libro> findByEstado(EstadoLibro estado) {
        return libroRepository.findByEstado(estado);
    }
    
    /**
     * Obtiene todos los libros disponibles para préstamo
     * @return lista de libros disponibles
     */
    @Transactional(readOnly = true)
    public List<Libro> findLibrosDisponibles() {
        return libroRepository.findLibrosDisponibles();
    }
    
    /**
     * Obtiene todos los libros prestados
     * @return lista de libros prestados
     */
    @Transactional(readOnly = true)
    public List<Libro> findLibrosPrestados() {
        return libroRepository.findLibrosPrestados();
    }
    
    /**
     * Busca libros por múltiples criterios
     * @param titulo título del libro (opcional)
     * @param autor autor del libro (opcional)
     * @param estado estado del libro (opcional)
     * @return lista de libros que coinciden con los criterios
     */
    @Transactional(readOnly = true)
    public List<Libro> findByCriterios(String titulo, String autor, EstadoLibro estado) {
        return libroRepository.findByCriterios(titulo, autor, estado);
    }
    
    /**
     * Cambia el estado de un libro
     * @param id el ID del libro
     * @param nuevoEstado el nuevo estado
     * @return el libro actualizado
     * @throws IllegalArgumentException si el libro no existe
     */
    public Libro cambiarEstado(Long id, EstadoLibro nuevoEstado) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe un libro con el ID: " + id));
        
        libro.setEstado(nuevoEstado);
        return libroRepository.save(libro);
    }
    
    /**
     * Cuenta libros por estado
     * @param estado el estado del libro
     * @return número de libros con el estado especificado
     */
    @Transactional(readOnly = true)
    public long countByEstado(EstadoLibro estado) {
        return libroRepository.countByEstado(estado);
    }
    
    /**
     * Verifica si un libro existe por su ID
     * @param id el ID del libro
     * @return true si existe, false en caso contrario
     */
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return libroRepository.existsById(id);
    }
}
