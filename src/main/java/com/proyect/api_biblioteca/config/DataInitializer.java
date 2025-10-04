package com.proyect.api_biblioteca.config;

import com.proyect.api_biblioteca.model.entity.Libro;
import com.proyect.api_biblioteca.model.entity.Usuario;
import com.proyect.api_biblioteca.model.entity.Prestamo;
import com.proyect.api_biblioteca.model.entity.TipoUsuario;
import com.proyect.api_biblioteca.model.entity.EstadoUsuario;
import com.proyect.api_biblioteca.model.entity.EstadoPrestamo;
import com.proyect.api_biblioteca.repository.LibroRepository;
import com.proyect.api_biblioteca.repository.UsuarioRepository;
import com.proyect.api_biblioteca.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Componente que inicializa la base de datos con datos de ejemplo
 */
@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private LibroRepository libroRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PrestamoRepository prestamoRepository;
    
    
    @Override
    public void run(String... args) throws Exception {
        // Verificar si ya existen datos para evitar duplicados
        if (libroRepository.count() == 0 && usuarioRepository.count() == 0) {
            inicializarDatos();
        }
    }
    
    private void inicializarDatos() {
        // Crear algunos libros de ejemplo
        Libro libro1 = new Libro();
        libro1.setTitulo("El Quijote");
        libro1.setAutor("Miguel de Cervantes");
        libro1.setIsbn("9788467034100");
        libro1.setAnioPublicacion(1605);
        libro1.setDescripcion("Obra maestra de la literatura española que narra las aventuras de Don Quijote de la Mancha");
        libro1.setNumeroPaginas(863);
        
        Libro libro2 = new Libro();
        libro2.setTitulo("Cien años de soledad");
        libro2.setAutor("Gabriel García Márquez");
        libro2.setIsbn("9788497592208");
        libro2.setAnioPublicacion(1967);
        libro2.setDescripcion("Novela que narra la historia de la familia Buendía a lo largo de siete generaciones");
        libro2.setNumeroPaginas(471);
        
        Libro libro3 = new Libro();
        libro3.setTitulo("1984");
        libro3.setAutor("George Orwell");
        libro3.setIsbn("9780452284234");
        libro3.setAnioPublicacion(1949);
        libro3.setDescripcion("Novela distópica que describe un mundo totalitario bajo vigilancia constante");
        libro3.setNumeroPaginas(328);
        
        Libro libro4 = new Libro();
        libro4.setTitulo("El señor de los anillos");
        libro4.setAutor("J.R.R. Tolkien");
        libro4.setIsbn("9780544003415");
        libro4.setAnioPublicacion(1954);
        libro4.setDescripcion("Epic fantasy que sigue las aventuras de Frodo y la Comunidad del Anillo");
        libro4.setNumeroPaginas(1216);
        
        Libro libro5 = new Libro();
        libro5.setTitulo("Don Juan Tenorio");
        libro5.setAutor("José Zorrilla");
        libro5.setIsbn("9788420674342");
        libro5.setAnioPublicacion(1844);
        libro5.setDescripcion("Drama romántico en verso que relata las aventuras del legendario Don Juan");
        libro5.setNumeroPaginas(200);
        libro5.setEstado(com.proyect.api_biblioteca.model.entity.EstadoLibro.PRESTADO);
        
        // Guardar los libros en la base de datos
        libroRepository.save(libro1);
        libroRepository.save(libro2);
        libroRepository.save(libro3);
        libroRepository.save(libro4);
        libroRepository.save(libro5);
        
        // Crear usuarios de ejemplo
        Usuario bibliotecario1 = new Usuario();
        bibliotecario1.setNombre("María");
        bibliotecario1.setApellido("González");
        bibliotecario1.setEmail("maria.gonzalez@biblioteca.com");
        bibliotecario1.setPassword("admin123");
        bibliotecario1.setTelefono("123456789");
        bibliotecario1.setDireccion("Calle Principal 123");
        bibliotecario1.setFechaNacimiento(LocalDate.of(1985, 5, 15));
        bibliotecario1.setTipoUsuario(TipoUsuario.BIBLIOTECARIO);
        bibliotecario1.setEstado(EstadoUsuario.ACTIVO);
        
        Usuario usuario1 = new Usuario();
        usuario1.setNombre("Juan");
        usuario1.setApellido("Pérez");
        usuario1.setEmail("juan.perez@email.com");
        usuario1.setPassword("123456");
        usuario1.setTelefono("987654321");
        usuario1.setDireccion("Avenida Central 456");
        usuario1.setFechaNacimiento(LocalDate.of(1990, 8, 22));
        usuario1.setTipoUsuario(TipoUsuario.USUARIO);
        usuario1.setEstado(EstadoUsuario.ACTIVO);
        
        Usuario usuario2 = new Usuario();
        usuario2.setNombre("Ana");
        usuario2.setApellido("Martínez");
        usuario2.setEmail("ana.martinez@email.com");
        usuario2.setPassword("123456");
        usuario2.setTelefono("555666777");
        usuario2.setDireccion("Plaza Mayor 789");
        usuario2.setFechaNacimiento(LocalDate.of(1995, 12, 3));
        usuario2.setTipoUsuario(TipoUsuario.USUARIO);
        usuario2.setEstado(EstadoUsuario.ACTIVO);
        
        // Guardar usuarios
        usuarioRepository.save(bibliotecario1);
        usuarioRepository.save(usuario1);
        usuarioRepository.save(usuario2);
        
        // Crear algunos préstamos de ejemplo
        Prestamo prestamo1 = new Prestamo();
        prestamo1.setUsuario(usuario1);
        prestamo1.setLibro(libro5); // Don Juan Tenorio (ya está marcado como prestado)
        prestamo1.setFechaPrestamo(LocalDate.now().minusDays(5));
        prestamo1.setFechaDevolucionEsperada(LocalDate.now().plusDays(10));
        prestamo1.setEstado(EstadoPrestamo.ACTIVO);
        
        Prestamo prestamo2 = new Prestamo();
        prestamo2.setUsuario(usuario2);
        prestamo2.setLibro(libro2); // Cien años de soledad
        prestamo2.setFechaPrestamo(LocalDate.now().minusDays(10));
        prestamo2.setFechaDevolucionEsperada(LocalDate.now().minusDays(5)); // Vencido
        prestamo2.setEstado(EstadoPrestamo.ACTIVO);
        
        // Guardar préstamos
        prestamoRepository.save(prestamo1);
        prestamoRepository.save(prestamo2);
        
        System.out.println("✅ Datos iniciales cargados correctamente en la base de datos H2");
        System.out.println("📚 Libros: " + libroRepository.count());
        System.out.println("👥 Usuarios: " + usuarioRepository.count());
        System.out.println("📖 Préstamos: " + prestamoRepository.count());
        System.out.println("🔑 Credenciales de acceso:");
        System.out.println("   Bibliotecario: maria.gonzalez@biblioteca.com / admin123");
        System.out.println("   Usuario: juan.perez@email.com / 123456");
        System.out.println("   Usuario: ana.martinez@email.com / 123456");
    }
}
