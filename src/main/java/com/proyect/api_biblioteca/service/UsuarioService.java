package com.proyect.api_biblioteca.service;

import com.proyect.api_biblioteca.model.entity.Usuario;
import com.proyect.api_biblioteca.model.entity.TipoUsuario;
import com.proyect.api_biblioteca.model.entity.EstadoUsuario;
import com.proyect.api_biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que maneja la lógica de negocio para la entidad Usuario
 */
@Service
@Transactional
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    
    /**
     * Obtiene todos los usuarios
     * @return lista de todos los usuarios
     */
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }
    
    /**
     * Busca un usuario por su ID
     * @param id el ID del usuario
     * @return Optional con el usuario si existe
     */
    @Transactional(readOnly = true)
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }
    
    /**
     * Busca un usuario por email
     * @param email el email del usuario
     * @return Optional con el usuario si existe
     */
    @Transactional(readOnly = true)
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
    
    /**
     * Guarda un nuevo usuario
     * @param usuario el usuario a guardar
     * @return el usuario guardado
     * @throws IllegalArgumentException si el email ya existe
     */
    public Usuario save(Usuario usuario) {
        // Verificar si el email ya existe
        if (usuario.getId() == null && usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario con el email: " + usuario.getEmail());
        }
        
        // Encriptar contraseña si es nuevo usuario
        if (usuario.getId() == null && usuario.getPassword() != null) {
            // Contraseña sin encriptar para ejercicio universitario
        }
        
        return usuarioRepository.save(usuario);
    }
    
    /**
     * Actualiza un usuario existente
     * @param id el ID del usuario a actualizar
     * @param usuario los nuevos datos del usuario
     * @return el usuario actualizado
     * @throws IllegalArgumentException si el usuario no existe o el email ya está en uso
     */
    public Usuario update(Long id, Usuario usuario) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("No existe un usuario con el ID: " + id);
        }
        
        // Verificar si el email ya existe en otro usuario
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioExistente.isPresent() && !usuarioExistente.get().getId().equals(id)) {
            throw new IllegalArgumentException("Ya existe otro usuario con el email: " + usuario.getEmail());
        }
        
        usuario.setId(id);
        
        // No actualizar la contraseña si no se proporciona
        if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
            Usuario usuarioActual = usuarioRepository.findById(id).orElseThrow();
            usuario.setPassword(usuarioActual.getPassword());
        } else {
            // Contraseña sin encriptar para ejercicio universitario
        }
        
        return usuarioRepository.save(usuario);
    }
    
    /**
     * Elimina un usuario por su ID
     * @param id el ID del usuario a eliminar
     * @throws IllegalArgumentException si el usuario no existe
     */
    public void deleteById(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("No existe un usuario con el ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }
    
    /**
     * Busca usuarios por tipo
     * @param tipoUsuario el tipo de usuario
     * @return lista de usuarios del tipo especificado
     */
    @Transactional(readOnly = true)
    public List<Usuario> findByTipoUsuario(TipoUsuario tipoUsuario) {
        return usuarioRepository.findByTipoUsuario(tipoUsuario);
    }
    
    /**
     * Busca usuarios por estado
     * @param estado el estado del usuario
     * @return lista de usuarios con el estado especificado
     */
    @Transactional(readOnly = true)
    public List<Usuario> findByEstado(EstadoUsuario estado) {
        return usuarioRepository.findByEstado(estado);
    }
    
    /**
     * Busca usuarios activos
     * @return lista de usuarios activos
     */
    @Transactional(readOnly = true)
    public List<Usuario> findUsuariosActivos() {
        return usuarioRepository.findUsuariosActivos();
    }
    
    /**
     * Busca bibliotecarios activos
     * @return lista de bibliotecarios activos
     */
    @Transactional(readOnly = true)
    public List<Usuario> findBibliotecariosActivos() {
        return usuarioRepository.findBibliotecariosActivos();
    }
    
    /**
     * Busca usuarios por múltiples criterios
     * @param nombre nombre del usuario (opcional)
     * @param apellido apellido del usuario (opcional)
     * @param tipoUsuario tipo de usuario (opcional)
     * @param estado estado del usuario (opcional)
     * @return lista de usuarios que coinciden con los criterios
     */
    @Transactional(readOnly = true)
    public List<Usuario> findByCriterios(String nombre, String apellido, TipoUsuario tipoUsuario, EstadoUsuario estado) {
        return usuarioRepository.findByCriterios(nombre, apellido, tipoUsuario, estado);
    }
    
    /**
     * Cambia el estado de un usuario
     * @param id el ID del usuario
     * @param nuevoEstado el nuevo estado
     * @return el usuario actualizado
     * @throws IllegalArgumentException si el usuario no existe
     */
    public Usuario cambiarEstado(Long id, EstadoUsuario nuevoEstado) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe un usuario con el ID: " + id));
        
        usuario.setEstado(nuevoEstado);
        return usuarioRepository.save(usuario);
    }
    
    /**
     * Promueve un usuario a bibliotecario
     * @param id el ID del usuario
     * @return el usuario actualizado
     * @throws IllegalArgumentException si el usuario no existe
     */
    public Usuario promoverABibliotecario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe un usuario con el ID: " + id));
        
        usuario.setTipoUsuario(TipoUsuario.BIBLIOTECARIO);
        return usuarioRepository.save(usuario);
    }
    
    /**
     * Cuenta usuarios por tipo
     * @param tipoUsuario el tipo de usuario
     * @return número de usuarios del tipo especificado
     */
    @Transactional(readOnly = true)
    public long countByTipoUsuario(TipoUsuario tipoUsuario) {
        return usuarioRepository.countByTipoUsuario(tipoUsuario);
    }
    
    /**
     * Cuenta usuarios por estado
     * @param estado el estado del usuario
     * @return número de usuarios con el estado especificado
     */
    @Transactional(readOnly = true)
    public long countByEstado(EstadoUsuario estado) {
        return usuarioRepository.countByEstado(estado);
    }
    
    /**
     * Verifica si un usuario existe por su ID
     * @param id el ID del usuario
     * @return true si existe, false en caso contrario
     */
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return usuarioRepository.existsById(id);
    }
}
