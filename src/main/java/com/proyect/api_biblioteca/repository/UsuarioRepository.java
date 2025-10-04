package com.proyect.api_biblioteca.repository;

import com.proyect.api_biblioteca.model.entity.Usuario;
import com.proyect.api_biblioteca.model.entity.TipoUsuario;
import com.proyect.api_biblioteca.model.entity.EstadoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para la entidad Usuario
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    /**
     * Busca un usuario por email
     * @param email el email del usuario
     * @return Optional con el usuario si existe
     */
    Optional<Usuario> findByEmail(String email);
    
    /**
     * Verifica si existe un usuario con el email especificado
     * @param email el email a verificar
     * @return true si existe, false en caso contrario
     */
    boolean existsByEmail(String email);
    
    /**
     * Busca usuarios por tipo
     * @param tipoUsuario el tipo de usuario
     * @return lista de usuarios del tipo especificado
     */
    List<Usuario> findByTipoUsuario(TipoUsuario tipoUsuario);
    
    /**
     * Busca usuarios por estado
     * @param estado el estado del usuario
     * @return lista de usuarios con el estado especificado
     */
    List<Usuario> findByEstado(EstadoUsuario estado);
    
    /**
     * Busca usuarios por nombre (ignorando mayúsculas/minúsculas)
     * @param nombre el nombre a buscar
     * @return lista de usuarios que coinciden con el nombre
     */
    List<Usuario> findByNombreContainingIgnoreCase(String nombre);
    
    /**
     * Busca usuarios por apellido (ignorando mayúsculas/minúsculas)
     * @param apellido el apellido a buscar
     * @return lista de usuarios que coinciden con el apellido
     */
    List<Usuario> findByApellidoContainingIgnoreCase(String apellido);
    
    /**
     * Busca usuarios activos
     * @return lista de usuarios activos
     */
    @Query("SELECT u FROM Usuario u WHERE u.estado = 'ACTIVO'")
    List<Usuario> findUsuariosActivos();
    
    /**
     * Busca bibliotecarios activos
     * @return lista de bibliotecarios activos
     */
    @Query("SELECT u FROM Usuario u WHERE u.tipoUsuario = 'BIBLIOTECARIO' AND u.estado = 'ACTIVO'")
    List<Usuario> findBibliotecariosActivos();
    
    /**
     * Cuenta usuarios por tipo
     * @param tipoUsuario el tipo de usuario
     * @return número de usuarios del tipo especificado
     */
    long countByTipoUsuario(TipoUsuario tipoUsuario);
    
    /**
     * Cuenta usuarios por estado
     * @param estado el estado del usuario
     * @return número de usuarios con el estado especificado
     */
    long countByEstado(EstadoUsuario estado);
    
    /**
     * Busca usuarios por múltiples criterios
     * @param nombre nombre del usuario (opcional)
     * @param apellido apellido del usuario (opcional)
     * @param tipoUsuario tipo de usuario (opcional)
     * @param estado estado del usuario (opcional)
     * @return lista de usuarios que coinciden con los criterios
     */
    @Query("SELECT u FROM Usuario u WHERE " +
           "(:nombre IS NULL OR LOWER(u.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) AND " +
           "(:apellido IS NULL OR LOWER(u.apellido) LIKE LOWER(CONCAT('%', :apellido, '%'))) AND " +
           "(:tipoUsuario IS NULL OR u.tipoUsuario = :tipoUsuario) AND " +
           "(:estado IS NULL OR u.estado = :estado)")
    List<Usuario> findByCriterios(@Param("nombre") String nombre, 
                                 @Param("apellido") String apellido, 
                                 @Param("tipoUsuario") TipoUsuario tipoUsuario,
                                 @Param("estado") EstadoUsuario estado);
}
