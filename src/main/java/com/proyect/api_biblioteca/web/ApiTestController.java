package com.proyect.api_biblioteca.web;

import com.proyect.api_biblioteca.model.entity.Libro;
import com.proyect.api_biblioteca.model.entity.Usuario;
import com.proyect.api_biblioteca.model.entity.Prestamo;
import com.proyect.api_biblioteca.service.LibroService;
import com.proyect.api_biblioteca.service.UsuarioService;
import com.proyect.api_biblioteca.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

/**
 * Controlador para la página de pruebas de la API
 */
@Controller
public class ApiTestController {

    @Autowired
    private LibroService libroService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private PrestamoService prestamoService;

    /**
     * Página de pruebas de la API
     */
    @GetMapping("/api-test")
    public String apiTest(Model model) {
        // Obtener datos para mostrar
        List<Libro> libros = libroService.findAll();
        List<Usuario> usuarios = usuarioService.findAll();
        List<Prestamo> prestamos = prestamoService.findAll();
        
        model.addAttribute("libros", libros);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("prestamos", prestamos);
        
        return "api-test";
    }

    /**
     * Crear un libro de prueba
     */
    @PostMapping("/api-test/create-book")
    public String createTestBook(RedirectAttributes redirectAttributes) {
        try {
            Libro libro = new Libro();
            libro.setTitulo("Libro de Prueba");
            libro.setAutor("Autor de Prueba");
            libro.setIsbn("1234567890123");
            libro.setAnioPublicacion(2024);
            libro.setDescripcion("Este es un libro creado para pruebas");
            libro.setNumeroPaginas(200);
            libro.setFechaIngreso(LocalDate.now());
            
            libroService.save(libro);
            redirectAttributes.addFlashAttribute("success", "Libro de prueba creado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear libro: " + e.getMessage());
        }
        return "redirect:/api-test";
    }

    /**
     * Crear un usuario de prueba
     */
    @PostMapping("/api-test/create-user")
    public String createTestUser(RedirectAttributes redirectAttributes) {
        try {
            Usuario usuario = new Usuario();
            usuario.setNombre("Usuario");
            usuario.setApellido("Prueba");
            usuario.setEmail("usuario.prueba@test.com");
            usuario.setPassword("123456");
            usuario.setTelefono("1234567890");
            usuario.setDireccion("Dirección de Prueba");
            usuario.setFechaNacimiento(LocalDate.of(1990, 1, 1));
            usuario.setTipoUsuario(com.proyect.api_biblioteca.model.entity.TipoUsuario.USUARIO);
            usuario.setEstado(com.proyect.api_biblioteca.model.entity.EstadoUsuario.ACTIVO);
            
            usuarioService.save(usuario);
            redirectAttributes.addFlashAttribute("success", "Usuario de prueba creado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear usuario: " + e.getMessage());
        }
        return "redirect:/api-test";
    }

    /**
     * Crear un préstamo de prueba
     */
    @PostMapping("/api-test/create-loan")
    public String createTestLoan(RedirectAttributes redirectAttributes) {
        try {
            List<Usuario> usuarios = usuarioService.findAll();
            List<Libro> libros = libroService.findLibrosDisponibles();
            
            if (usuarios.isEmpty() || libros.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Necesitas tener usuarios y libros disponibles para crear un préstamo");
                return "redirect:/api-test";
            }
            
            prestamoService.crearPrestamo(usuarios.get(0).getId(), libros.get(0).getId());
            redirectAttributes.addFlashAttribute("success", "Préstamo de prueba creado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear préstamo: " + e.getMessage());
        }
        return "redirect:/api-test";
    }

    /**
     * Eliminar un libro
     */
    @PostMapping("/api-test/delete-book")
    public String deleteBook(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            libroService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Libro eliminado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar libro: " + e.getMessage());
        }
        return "redirect:/api-test";
    }

    /**
     * Devolver un libro
     */
    @PostMapping("/api-test/return-book")
    public String returnBook(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            prestamoService.devolverLibro(id, "Devolución de prueba");
            redirectAttributes.addFlashAttribute("success", "Libro devuelto exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al devolver libro: " + e.getMessage());
        }
        return "redirect:/api-test";
    }
}
