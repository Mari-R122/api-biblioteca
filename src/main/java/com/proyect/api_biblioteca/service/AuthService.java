package com.proyect.api_biblioteca.service;

import com.proyect.api_biblioteca.dto.LoginRequestDTO;
import com.proyect.api_biblioteca.dto.LoginResponseDTO;
import com.proyect.api_biblioteca.model.entity.Usuario;
import com.proyect.api_biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Servicio de autenticación
 */
@Service
public class AuthService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    
    /**
     * Autentica un usuario con email y contraseña
     * @param loginRequest datos de login
     * @return respuesta de login con token (simulado)
     */
    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(loginRequest.getEmail());
        
        if (usuarioOpt.isEmpty()) {
            return new LoginResponseDTO("Credenciales inválidas");
        }
        
        Usuario usuario = usuarioOpt.get();
        
        // Verificar contraseña
        if (!loginRequest.getPassword().equals(usuario.getPassword())) {
            return new LoginResponseDTO("Credenciales inválidas");
        }
        
        // Verificar que el usuario esté activo
        if (!usuario.isActivo()) {
            return new LoginResponseDTO("Usuario inactivo");
        }
        
        // Actualizar último acceso
        usuario.setUltimoAcceso(LocalDateTime.now());
        usuarioRepository.save(usuario);
        
        // Generar token simulado (en producción usar JWT)
        String token = generateSimpleToken(usuario);
        
        return new LoginResponseDTO(
            token,
            usuario.getEmail(),
            usuario.getNombre(),
            usuario.getApellido(),
            usuario.getTipoUsuario()
        );
    }
    
    /**
     * Registra un nuevo usuario
     * @param usuario el usuario a registrar
     * @return el usuario registrado
     */
    public Usuario register(Usuario usuario) {
        // Verificar si el email ya existe
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario con el email: " + usuario.getEmail());
        }
        
        // Encriptar contraseña
        // Contraseña sin encriptar para ejercicio universitario
        
        // Por defecto, los nuevos usuarios son de tipo USUARIO
        usuario.setTipoUsuario(com.proyect.api_biblioteca.model.entity.TipoUsuario.USUARIO);
        
        return usuarioRepository.save(usuario);
    }
    
    /**
     * Valida un token (simulado)
     * @param token el token a validar
     * @return el usuario si el token es válido
     */
    public Usuario validateToken(String token) {
        // En una implementación real, aquí se validaría el JWT
        // Por ahora, simulamos la validación
        if (token == null || !token.startsWith("Bearer_")) {
            return null;
        }
        
        try {
            String userId = token.substring(7); // Remover "Bearer_"
            Long id = Long.parseLong(userId);
            return usuarioRepository.findById(id).orElse(null);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    /**
     * Genera un token simple para el usuario
     * @param usuario el usuario
     * @return el token generado
     */
    private String generateSimpleToken(Usuario usuario) {
        // En producción, usar JWT con firma digital
        return "Bearer_" + usuario.getId();
    }
}
