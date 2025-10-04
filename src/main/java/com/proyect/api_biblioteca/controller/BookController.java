package com.proyect.api_biblioteca.controller;

import com.proyect.api_biblioteca.model.entity.Libro;
import com.proyect.api_biblioteca.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para libros - Ejercicio Universitario
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private LibroService libroService;

    /**
     * Obtener todos los libros
     */
    @GetMapping
    public List<Libro> getAllBooks() {
        return libroService.findAll();
    }

    /**
     * Crear un nuevo libro
     */
    @PostMapping
    public Libro addBook(@RequestBody Libro libro) {
        return libroService.save(libro);
    }

    /**
     * Obtener un libro por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Libro> getBook(@PathVariable Long id) {
        Optional<Libro> libro = libroService.findById(id);
        return libro.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Actualizar un libro
     */
    @PutMapping("/{id}")
    public ResponseEntity<Libro> updateBook(@PathVariable Long id, @RequestBody Libro libro) {
        try {
            Libro updatedLibro = libroService.update(id, libro);
            return ResponseEntity.ok(updatedLibro);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Eliminar un libro
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        try {
            libroService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
