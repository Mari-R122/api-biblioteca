# API Biblioteca - Sistema Completo de Gestión de Biblioteca

Esta es una API RESTful completa desarrollada con Spring Boot para la gestión integral de una biblioteca municipal, implementando todos los requerimientos del ejercicio de profundización.

## 🚀 Características Principales

- **Base de datos H2 embebida** - No requiere configuración externa
- **JPA/Hibernate** - Mapeo objeto-relacional completo
- **Sistema de autenticación y autorización** - Roles de bibliotecario y usuario
- **Gestión completa de usuarios** - Registro, login y administración
- **Sistema de préstamos** - Creación, devolución, renovación y seguimiento
- **Validación de datos** - Validaciones automáticas en todas las entidades
- **Controladores RESTful** - Endpoints completos siguiendo principios REST
- **Manejo de excepciones** - Respuestas consistentes de error
- **Datos de ejemplo** - Base de datos poblada automáticamente
- **Seguridad robusta** - Encriptación de contraseñas y control de acceso

## 🛠️ Tecnologías Utilizadas

- Java 17
- Spring Boot 3.5.6
- Spring Data JPA
- Spring Security
- H2 Database
- Spring Validation
- Maven

## 📋 Prerrequisitos

- Java 17 o superior
- Maven 3.6 o superior

## 🏃‍♂️ Ejecutar la Aplicación

1. **Clonar el repositorio:**
   ```bash
   git clone <url-del-repositorio>
   cd api-biblioteca
   ```

2. **Compilar y ejecutar:**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

3. **Acceder a la aplicación:**
   - API: http://localhost:8080/api/libros
   - Consola H2: http://localhost:8080/h2-console
     - JDBC URL: `jdbc:h2:mem:testdb`
     - Usuario: `sa`
     - Contraseña: `password`

## 📚 Endpoints de la API

### 🔐 Autenticación (Público)
- `POST /api/auth/login` - Iniciar sesión
- `POST /api/auth/register` - Registro de usuario
- `GET /api/auth/validate` - Validar token

### 📖 Gestión de Libros (Público)
- `GET /api/libros` - Obtener todos los libros
- `GET /api/libros/{id}` - Obtener libro por ID
- `GET /api/libros/disponibles` - Obtener libros disponibles
- `GET /api/libros/prestados` - Obtener libros prestados
- `GET /api/libros/estadisticas` - Obtener estadísticas

### 🔍 Búsqueda de Libros (Público)
- `GET /api/libros/buscar/titulo?q={titulo}` - Buscar por título
- `GET /api/libros/buscar/autor?autor={autor}` - Buscar por autor
- `GET /api/libros/buscar/isbn/{isbn}` - Buscar por ISBN
- `GET /api/libros/estado/{estado}` - Buscar por estado
- `GET /api/libros/buscar?titulo={titulo}&autor={autor}&estado={estado}` - Búsqueda múltiple

### 👥 Gestión de Usuarios (Autenticado)
- `GET /api/usuarios` - Obtener todos los usuarios
- `GET /api/usuarios/{id}` - Obtener usuario por ID
- `POST /api/usuarios` - Crear nuevo usuario
- `PUT /api/usuarios/{id}` - Actualizar usuario
- `DELETE /api/usuarios/{id}` - Eliminar usuario
- `GET /api/usuarios/activos` - Obtener usuarios activos
- `GET /api/usuarios/bibliotecarios` - Obtener bibliotecarios
- `PATCH /api/usuarios/{id}/estado` - Cambiar estado del usuario
- `PATCH /api/usuarios/{id}/promover` - Promover a bibliotecario

### 📋 Gestión de Préstamos (Autenticado)
- `GET /api/prestamos` - Obtener todos los préstamos
- `GET /api/prestamos/{id}` - Obtener préstamo por ID
- `POST /api/prestamos` - Crear nuevo préstamo
- `PATCH /api/prestamos/{id}/devolver` - Devolver libro
- `PATCH /api/prestamos/{id}/renovar` - Renovar préstamo
- `GET /api/prestamos/activos` - Obtener préstamos activos
- `GET /api/prestamos/vencidos` - Obtener préstamos vencidos
- `GET /api/prestamos/usuario/{usuarioId}` - Préstamos por usuario
- `GET /api/prestamos/libro/{libroId}` - Préstamos por libro

## 📝 Estructura de Datos

### Entidad Libro
```json
{
  "id": 1,
  "titulo": "El Quijote",
  "autor": "Miguel de Cervantes",
  "descripcion": "Obra maestra de la literatura española...",
  "anioPublicacion": 1605,
  "isbn": "9788467034100",
  "numeroPaginas": 863,
  "fechaIngreso": "2024-01-15",
  "estado": "DISPONIBLE"
}
```

### Entidad Usuario
```json
{
  "id": 1,
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan.perez@email.com",
  "telefono": "987654321",
  "direccion": "Avenida Central 456",
  "fechaNacimiento": "1990-08-22",
  "tipoUsuario": "USUARIO",
  "estado": "ACTIVO"
}
```

### Entidad Préstamo
```json
{
  "id": 1,
  "usuarioId": 1,
  "libroId": 2,
  "fechaPrestamo": "2024-10-01",
  "fechaDevolucionEsperada": "2024-10-16",
  "fechaDevolucionReal": null,
  "estado": "ACTIVO",
  "observaciones": null,
  "multa": null,
  "nombreUsuario": "Juan Pérez",
  "tituloLibro": "Cien años de soledad",
  "autorLibro": "Gabriel García Márquez"
}
```

### Estados de Libro
- `DISPONIBLE` - Disponible para préstamo
- `PRESTADO` - Prestado a un usuario
- `EN_REPARACION` - En reparación
- `PERDIDO` - Perdido
- `RETIRADO` - Retirado del sistema

### Tipos de Usuario
- `BIBLIOTECARIO` - Acceso completo al sistema
- `USUARIO` - Puede realizar préstamos y consultas

### Estados de Usuario
- `ACTIVO` - Usuario activo en el sistema
- `INACTIVO` - Usuario temporalmente deshabilitado
- `SUSPENDIDO` - Usuario suspendido por violaciones
- `BLOQUEADO` - Usuario bloqueado permanentemente

### Estados de Préstamo
- `ACTIVO` - Préstamo en curso
- `DEVUELTO` - Libro devuelto correctamente
- `VENCIDO` - Préstamo vencido sin devolver
- `PERDIDO` - Libro perdido durante el préstamo
- `RENOVADO` - Préstamo renovado
- `CANCELADO` - Préstamo cancelado

## 🔧 Ejemplos de Uso

### 1. Autenticación
```bash
# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "juan.perez@email.com",
    "password": "123456"
  }'

# Registro de usuario
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Pedro",
    "apellido": "García",
    "email": "pedro.garcia@email.com",
    "telefono": "111222333",
    "direccion": "Calle Nueva 123",
    "fechaNacimiento": "1992-03-15"
  }'
```

### 2. Gestión de Libros
```bash
# Crear un nuevo libro
curl -X POST http://localhost:8080/api/libros \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "El Principito",
    "autor": "Antoine de Saint-Exupéry",
    "descripcion": "Cuento filosófico para niños y adultos",
    "anioPublicacion": 1943,
    "isbn": "9782070612758",
    "numeroPaginas": 96
  }'

# Buscar libros por autor
curl "http://localhost:8080/api/libros/buscar/autor?autor=Cervantes"

# Cambiar estado de un libro
curl -X PATCH http://localhost:8080/api/libros/1/estado \
  -H "Content-Type: application/json" \
  -d "PRESTADO"
```

### 3. Gestión de Préstamos
```bash
# Crear un préstamo (requiere autenticación)
curl -X POST http://localhost:8080/api/prestamos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer_2" \
  -d '{
    "usuarioId": 2,
    "libroId": 1
  }'

# Devolver un libro
curl -X PATCH http://localhost:8080/api/prestamos/1/devolver \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer_2" \
  -d '{
    "observaciones": "Libro devuelto en buen estado"
  }'

# Renovar un préstamo
curl -X PATCH http://localhost:8080/api/prestamos/1/renovar \
  -H "Authorization: Bearer_2"
```

### 4. Gestión de Usuarios
```bash
# Obtener todos los usuarios (requiere autenticación)
curl -H "Authorization: Bearer_1" http://localhost:8080/api/usuarios

# Promover usuario a bibliotecario
curl -X PATCH http://localhost:8080/api/usuarios/2/promover \
  -H "Authorization: Bearer_1"

# Cambiar estado de usuario
curl -X PATCH http://localhost:8080/api/usuarios/2/estado \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer_1" \
  -d "INACTIVO"
```

## 🗃️ Base de Datos H2

La aplicación utiliza H2 como base de datos embebida en memoria. Esto significa que:

- ✅ **No requiere instalación** de base de datos externa
- ✅ **Inicio rápido** de la aplicación
- ✅ **Datos de ejemplo** cargados automáticamente
- ⚠️ **Los datos se pierden** al reiniciar la aplicación

### Acceder a la consola H2
1. Ejecutar la aplicación
2. Ir a http://localhost:8080/h2-console
3. Usar las credenciales:
   - JDBC URL: `jdbc:h2:mem:testdb`
   - Usuario: `sa`
   - Contraseña: `password`

## 🔑 Credenciales de Prueba

Al iniciar la aplicación, se crean automáticamente los siguientes usuarios:

### Bibliotecario (Acceso completo)
- **Email:** `maria.gonzalez@biblioteca.com`
- **Contraseña:** `admin123`
- **Token:** `Bearer_1`

### Usuarios Regulares
- **Email:** `juan.perez@email.com`
- **Contraseña:** `123456`
- **Token:** `Bearer_2`

- **Email:** `ana.martinez@email.com`
- **Contraseña:** `123456`
- **Token:** `Bearer_3`

## 📊 Datos de Ejemplo

La aplicación incluye datos de ejemplo:

- **5 libros** de literatura clásica
- **3 usuarios** (1 bibliotecario, 2 usuarios regulares)
- **2 préstamos** (1 activo, 1 vencido)

## 📁 Estructura del Proyecto

```
src/main/java/com/proyect/api_biblioteca/
├── ApiBibliotecaApplication.java          # Clase principal
├── config/
│   ├── DataInitializer.java              # Inicialización de datos
│   ├── GlobalExceptionHandler.java       # Manejo de excepciones
│   └── SecurityConfig.java               # Configuración de seguridad
├── controller/
│   ├── AuthController.java               # Controlador de autenticación
│   ├── LibroController.java              # Controlador de libros
│   ├── UsuarioController.java            # Controlador de usuarios
│   └── PrestamoController.java           # Controlador de préstamos
├── dto/
│   ├── LoginRequestDTO.java              # DTO para login
│   ├── LoginResponseDTO.java             # DTO para respuesta de login
│   ├── UsuarioDTO.java                   # DTO para usuarios
│   └── PrestamoDTO.java                  # DTO para préstamos
├── model/entity/
│   ├── Libro.java                        # Entidad libro
│   ├── Usuario.java                      # Entidad usuario
│   ├── Prestamo.java                     # Entidad préstamo
│   ├── EstadoLibro.java                  # Enum estados de libro
│   ├── EstadoUsuario.java                # Enum estados de usuario
│   ├── EstadoPrestamo.java               # Enum estados de préstamo
│   └── TipoUsuario.java                  # Enum tipos de usuario
├── repository/
│   ├── LibroRepository.java              # Repositorio de libros
│   ├── UsuarioRepository.java            # Repositorio de usuarios
│   └── PrestamoRepository.java           # Repositorio de préstamos
└── service/
    ├── LibroService.java                 # Servicio de libros
    ├── UsuarioService.java               # Servicio de usuarios
    ├── PrestamoService.java              # Servicio de préstamos
    └── AuthService.java                  # Servicio de autenticación
```

## 🧪 Testing

Para ejecutar las pruebas:
```bash
mvn test
```

## 📖 Documentación Adicional

- [Spring Boot Reference](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [H2 Database](https://www.h2database.com/html/main.html)

## 🤝 Contribuir

1. Fork el proyecto
2. Crear una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.
