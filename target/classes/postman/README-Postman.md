# Colección de Postman - API Biblioteca

## 📋 Descripción

Esta colección de Postman contiene todos los endpoints de la API de Biblioteca organizados por funcionalidad, con ejemplos de peticiones y respuestas para facilitar las pruebas y el desarrollo.

## 🚀 Configuración Inicial

### 1. Importar la Colección

1. Abre Postman
2. Haz clic en "Import" en la esquina superior izquierda
3. Selecciona el archivo `Api-Biblioteca-Collection.json`
4. La colección se importará con todas las carpetas organizadas

### 2. Configurar Variables de Entorno

La colección incluye variables predefinidas:

- **`base_url`**: `http://localhost:8080` (URL base de la API)
- **`auth_token`**: Token JWT para autenticación (se llena automáticamente)

### 3. Variables de Entorno Recomendadas

Crea un entorno en Postman con estas variables:

```json
{
  "base_url": "http://localhost:8080",
  "auth_token": "",
  "user_email": "juan.perez@email.com",
  "user_password": "123456",
  "admin_email": "maria.gonzalez@biblioteca.com",
  "admin_password": "admin123"
}
```

## 📁 Estructura de la Colección

### 🔐 Autenticación
- **Login Usuario** - Iniciar sesión como usuario regular
- **Login Bibliotecario** - Iniciar sesión como bibliotecario
- **Registrar Usuario** - Crear nuevo usuario
- **Validar Token** - Verificar token de autenticación

### 📚 Libros
- **CRUD Completo**: Crear, leer, actualizar, eliminar libros
- **Búsquedas**: Por título, autor, ISBN
- **Filtros**: Por estado, disponibles, prestados
- **Gestión de Estados**: Cambiar estado de libros
- **Estadísticas**: Conteo por estado

### 👥 Usuarios
- **CRUD Completo**: Gestión de usuarios
- **Búsquedas**: Por criterios múltiples
- **Filtros**: Por tipo, estado, activos
- **Gestión**: Cambiar estado, promover a bibliotecario
- **Estadísticas**: Conteo por tipo y estado

### 📖 Préstamos
- **Gestión Completa**: Crear, devolver, renovar préstamos
- **Consultas**: Por usuario, libro, estado
- **Estados**: Activos, vencidos, devueltos
- **Estadísticas**: Conteo por estado

## 🔑 Autenticación JWT

### Flujo de Autenticación

1. **Hacer Login**:
   ```
   POST /api/auth/login
   {
     "email": "juan.perez@email.com",
     "password": "123456"
   }
   ```

2. **Copiar el Token**:
   ```json
   {
     "token": "Bearer_1",
     "email": "juan.perez@email.com",
     "nombre": "Juan",
     "apellido": "Pérez",
     "tipoUsuario": "USUARIO"
   }
   ```

3. **Configurar Variable**:
   - Copia el valor del token (ej: `Bearer_1`)
   - Pega en la variable `auth_token` del entorno

4. **Usar en Peticiones**:
   - Todas las peticiones protegidas incluyen automáticamente:
   ```
   Authorization: Bearer {{auth_token}}
   ```

### Script de Automatización (Opcional)

Puedes agregar este script en el evento "Test" de la petición de Login:

```javascript
if (pm.response.code === 200) {
    const responseJson = pm.response.json();
    if (responseJson.token) {
        pm.environment.set("auth_token", responseJson.token);
        console.log("Token guardado automáticamente:", responseJson.token);
    }
}
```

## 📊 Datos de Prueba Incluidos

### Usuarios Predefinidos

**Usuario Regular:**
- Email: `juan.perez@email.com`
- Password: `123456`
- Tipo: USUARIO

**Bibliotecario:**
- Email: `maria.gonzalez@biblioteca.com`
- Password: `admin123`
- Tipo: BIBLIOTECARIO

**Usuario Adicional:**
- Email: `ana.martinez@email.com`
- Password: `123456`
- Tipo: USUARIO

### Libros de Ejemplo

La aplicación se inicializa con 5 libros de ejemplo:
1. "Cien años de soledad" - Gabriel García Márquez
2. "Don Quijote de la Mancha" - Miguel de Cervantes
3. "1984" - George Orwell
4. "Orgullo y prejuicio" - Jane Austen
5. "El Gran Gatsby" - F. Scott Fitzgerald

## 🧪 Escenarios de Prueba

### 1. Flujo Completo de Usuario
```
1. Login como usuario → Obtener token
2. Buscar libros disponibles
3. Ver préstamos del usuario
4. Validar token
```

### 2. Flujo Completo de Bibliotecario
```
1. Login como bibliotecario → Obtener token
2. Obtener todos los usuarios
3. Crear nuevo libro
4. Crear préstamo
5. Ver estadísticas
6. Devolver libro
```

### 3. Gestión de Libros
```
1. Obtener todos los libros
2. Buscar por título/autor
3. Crear nuevo libro
4. Actualizar libro
5. Cambiar estado
6. Ver estadísticas
```

### 4. Gestión de Préstamos
```
1. Ver préstamos activos
2. Crear préstamo
3. Renovar préstamo
4. Devolver libro
5. Ver préstamos vencidos
```

## 🔍 Códigos de Respuesta

### Éxito
- **200 OK**: Petición exitosa
- **201 Created**: Recurso creado
- **204 No Content**: Eliminación exitosa

### Errores de Cliente
- **400 Bad Request**: Datos inválidos
- **401 Unauthorized**: Token inválido o expirado
- **403 Forbidden**: Sin permisos suficientes
- **404 Not Found**: Recurso no encontrado

### Errores de Servidor
- **500 Internal Server Error**: Error interno

## 📝 Ejemplos de Uso

### Crear un Nuevo Libro
```json
POST /api/libros
Authorization: Bearer {{auth_token}}
Content-Type: application/json

{
  "titulo": "El Principito",
  "autor": "Antoine de Saint-Exupéry",
  "descripcion": "Una obra maestra de la literatura francesa",
  "anioPublicacion": 1943,
  "isbn": "9782070413558",
  "numeroPaginas": 96,
  "estado": "DISPONIBLE"
}
```

### Crear un Préstamo
```json
POST /api/prestamos
Authorization: Bearer {{auth_token}}
Content-Type: application/json

{
  "usuarioId": 1,
  "libroId": 1
}
```

### Buscar Libros con Criterios Múltiples
```
GET /api/libros/buscar?titulo=quijote&autor=cervantes&estado=DISPONIBLE
Authorization: Bearer {{auth_token}}
```

## 🛠️ Personalización

### Agregar Nuevos Endpoints

1. Duplica un endpoint existente
2. Modifica la URL y método HTTP
3. Actualiza el cuerpo de la petición
4. Ajusta la descripción

### Modificar Variables

1. Ve a la pestaña "Variables" de la colección
2. Edita `base_url` si cambias el puerto
3. Ajusta `auth_token` si cambias el formato del token

### Agregar Tests Automatizados

En la pestaña "Tests" de cada petición:

```javascript
// Verificar código de respuesta
pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});

// Verificar estructura de respuesta
pm.test("Response has required fields", function () {
    const jsonData = pm.response.json();
    pm.expect(jsonData).to.have.property('id');
    pm.expect(jsonData).to.have.property('titulo');
});

// Verificar tiempo de respuesta
pm.test("Response time is less than 2000ms", function () {
    pm.expect(pm.response.responseTime).to.be.below(2000);
});
```

## 🚨 Notas Importantes

1. **Autenticación Requerida**: La mayoría de endpoints requieren token JWT
2. **Roles de Usuario**: Algunas operaciones requieren rol de bibliotecario
3. **Validaciones**: Los datos enviados deben cumplir las validaciones definidas
4. **Base de Datos H2**: Los datos se reinician al reiniciar la aplicación
5. **Console H2**: Accesible en `http://localhost:8080/h2-console`

## 📞 Soporte

Si encuentras problemas:

1. Verifica que la aplicación esté ejecutándose en `localhost:8080`
2. Confirma que el token de autenticación sea válido
3. Revisa los logs de la aplicación para errores
4. Consulta la documentación de la API en el README principal

## 🔄 Actualizaciones

Esta colección se actualiza junto con la API. Para obtener la versión más reciente:

1. Descarga el archivo actualizado
2. Importa la nueva versión en Postman
3. Actualiza las variables si es necesario

---

**¡Disfruta probando la API de Biblioteca! 📚✨**
