package com.proyect.api_biblioteca.controller;

import com.proyect.api_biblioteca.model.entity.Usuario;
import com.proyect.api_biblioteca.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para usuarios - Ejercicio Universitario
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Obtener todos los usuarios
     */
    @GetMapping
    public List<Usuario> getAllUsers() {
        return usuarioService.findAll();
    }

    /**
     * Crear un nuevo usuario
     */
    @PostMapping
    public Usuario addUser(@RequestBody Usuario usuario) {
        return usuarioService.save(usuario);
    }

    /**
     * Obtener un usuario por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUser(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.findById(id);
        return usuario.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Actualizar un usuario
     */
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUser(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            Usuario updatedUsuario = usuarioService.update(id, usuario);
            return ResponseEntity.ok(updatedUsuario);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Eliminar un usuario
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            usuarioService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
