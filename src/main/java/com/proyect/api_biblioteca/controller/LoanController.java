package com.proyect.api_biblioteca.controller;

import com.proyect.api_biblioteca.model.entity.Prestamo;
import com.proyect.api_biblioteca.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para préstamos - Ejercicio Universitario
 */
@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private PrestamoService prestamoService;

    /**
     * Obtener todos los préstamos
     */
    @GetMapping
    public List<Prestamo> getAllLoans() {
        return prestamoService.findAll();
    }

    /**
     * Crear un nuevo préstamo
     */
    @PostMapping
    public Prestamo createLoan(@RequestBody CreateLoanRequest request) {
        return prestamoService.crearPrestamo(request.getUsuarioId(), request.getLibroId());
    }

    /**
     * Obtener un préstamo por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> getLoan(@PathVariable Long id) {
        Optional<Prestamo> prestamo = prestamoService.findById(id);
        return prestamo.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Devolver un libro
     */
    @PutMapping("/{id}/return")
    public ResponseEntity<Prestamo> returnBook(@PathVariable Long id) {
        try {
            prestamoService.devolverLibro(id, "Libro devuelto");
            Optional<Prestamo> prestamo = prestamoService.findById(id);
            return prestamo.map(ResponseEntity::ok)
                          .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Clase interna para el request de crear préstamo
     */
    public static class CreateLoanRequest {
        private Long usuarioId;
        private Long libroId;

        // Getters y setters
        public Long getUsuarioId() { return usuarioId; }
        public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
        public Long getLibroId() { return libroId; }
        public void setLibroId(Long libroId) { this.libroId = libroId; }
    }
}
