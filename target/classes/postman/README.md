# 📚 Colección de Postman - API Biblioteca

Esta carpeta contiene todos los archivos necesarios para probar la API de Biblioteca usando Postman.

## 📁 Archivos Incluidos

### 🔧 Archivos de Configuración
- **`Api-Biblioteca-Collection.json`** - Colección principal con todos los endpoints
- **`Api-Biblioteca-Environment.json`** - Variables de entorno preconfiguradas

### 📖 Documentación
- **`README-Postman.md`** - Guía detallada de uso de Postman
- **`README.md`** - Este archivo con resumen general

### 🛠️ Recursos Adicionales
- **`Postman-Scripts.js`** - Scripts de automatización y validación
- **`Sample-Data.json`** - Datos de ejemplo para pruebas

## 🚀 Inicio Rápido

### 1. Importar en Postman
```
1. Abre Postman
2. Click en "Import"
3. Selecciona "Api-Biblioteca-Collection.json"
4. Selecciona "Api-Biblioteca-Environment.json"
```

### 2. Configurar Entorno
```
1. Selecciona el entorno "API Biblioteca - Entorno Local"
2. Verifica que base_url = "http://localhost:8080"
3. Ejecuta "Login Usuario" para obtener el token
```

### 3. ¡Comenzar a Probar!
```
1. Usa los endpoints organizados por carpetas
2. Los datos de ejemplo están en Sample-Data.json
3. Los scripts automáticos están en Postman-Scripts.js
```

## 📋 Características

### ✅ **Colección Completa**
- **47 endpoints** organizados en 4 categorías
- **Autenticación JWT** automática
- **Variables de entorno** preconfiguradas
- **Scripts de validación** automáticos

### ✅ **Categorías de Endpoints**
- 🔐 **Autenticación** (4 endpoints)
- 📚 **Libros** (14 endpoints)
- 👥 **Usuarios** (12 endpoints)
- 📖 **Préstamos** (12 endpoints)

### ✅ **Funcionalidades Avanzadas**
- **Login automático** con guardado de token
- **Validación de respuestas** automática
- **Datos de prueba** incluidos
- **Scripts de utilidad** para debugging

## 🎯 Casos de Uso

### 👤 **Para Desarrolladores**
- Probar endpoints durante desarrollo
- Validar respuestas y códigos de estado
- Debugging de problemas de API
- Documentación interactiva

### 🧪 **Para Testing**
- Ejecutar suites de pruebas
- Validar reglas de negocio
- Probar flujos completos
- Automatizar validaciones

### 📖 **Para Documentación**
- Explorar la API de forma interactiva
- Ver ejemplos de peticiones y respuestas
- Entender el flujo de autenticación
- Aprender la estructura de datos

## 🔑 Credenciales de Prueba

### Usuario Regular
```
Email: juan.perez@email.com
Password: 123456
```

### Bibliotecario
```
Email: maria.gonzalez@biblioteca.com
Password: admin123
```

### Usuario Adicional
```
Email: ana.martinez@email.com
Password: 123456
```

## 📊 Estadísticas de la Colección

| Categoría | Endpoints | Descripción |
|-----------|-----------|-------------|
| 🔐 Autenticación | 4 | Login, registro, validación |
| 📚 Libros | 14 | CRUD, búsquedas, filtros, estadísticas |
| 👥 Usuarios | 12 | CRUD, búsquedas, gestión de roles |
| 📖 Préstamos | 12 | Gestión completa del ciclo de préstamos |
| **TOTAL** | **42** | **API completa de biblioteca** |

## 🛠️ Personalización

### Modificar URL Base
```json
// En Api-Biblioteca-Environment.json
{
  "key": "base_url",
  "value": "https://tu-api.com"  // Cambiar aquí
}
```

### Agregar Nuevos Endpoints
1. Duplica un endpoint existente
2. Modifica URL, método y datos
3. Actualiza la descripción
4. Agrega scripts de validación si es necesario

### Crear Tests Personalizados
```javascript
// En la pestaña "Tests" de cualquier endpoint
pm.test("Mi test personalizado", function () {
    // Tu lógica de validación aquí
    pm.expect(pm.response.code).to.equal(200);
});
```

## 🔍 Troubleshooting

### ❌ **Error 401 - Unauthorized**
```
Solución: Ejecuta "Login Usuario" primero para obtener el token
```

### ❌ **Error 403 - Forbidden**
```
Solución: Asegúrate de estar logueado como bibliotecario para operaciones administrativas
```

### ❌ **Error 404 - Not Found**
```
Solución: Verifica que la aplicación esté ejecutándose en localhost:8080
```

### ❌ **Token no se guarda automáticamente**
```
Solución: Agrega el script de login automático desde Postman-Scripts.js
```

## 📞 Soporte

### Documentación Adicional
- `README-Postman.md` - Guía detallada de uso
- `Sample-Data.json` - Datos de ejemplo
- `Postman-Scripts.js` - Scripts de automatización

### Problemas Comunes
1. **Aplicación no responde**: Verifica que esté ejecutándose
2. **Token expirado**: Haz login nuevamente
3. **Permisos insuficientes**: Usa credenciales de bibliotecario
4. **Datos no encontrados**: Verifica que existan en la base de datos

## 🔄 Actualizaciones

Esta colección se mantiene actualizada con la API. Para obtener la última versión:

1. Descarga los archivos actualizados
2. Importa la nueva colección en Postman
3. Actualiza las variables de entorno si es necesario

---

## 🎉 ¡Disfruta Probando la API!

Con esta colección tienes todo lo necesario para:
- ✅ Probar todos los endpoints
- ✅ Validar respuestas automáticamente  
- ✅ Automatizar el login
- ✅ Ejecutar flujos completos
- ✅ Debuggear problemas
- ✅ Documentar la API

**¡La API de Biblioteca está lista para ser probada! 📚✨**
