# 🚀 Instrucciones de Importación - Postman

## 📋 Pasos para Importar la Colección

### Paso 1: Abrir Postman
1. Abre la aplicación Postman
2. Si no tienes Postman, descárgalo desde: https://www.postman.com/downloads/

### Paso 2: Importar la Colección
1. **Click en "Import"** (esquina superior izquierda)
2. **Selecciona los archivos**:
   - `Api-Biblioteca-Collection.json`
   - `Api-Biblioteca-Environment.json`
3. **Click "Import"**

### Paso 3: Configurar el Entorno
1. **Selecciona el entorno** "API Biblioteca - Entorno Local" en el dropdown superior derecho
2. **Verifica las variables**:
   - `base_url`: `http://localhost:8080`
   - `auth_token`: (vacío inicialmente)

### Paso 4: Configurar Scripts Automáticos (Opcional)
1. **Abre la petición "Login Usuario"**
2. **Ve a la pestaña "Tests"**
3. **Copia y pega** el script de "LOGIN AUTOMÁTICO" desde `Postman-Scripts.js`
4. **Guarda la colección**

### Paso 5: ¡Comenzar a Probar!
1. **Ejecuta la aplicación** Spring Boot
2. **Haz login** con "Login Usuario"
3. **El token se guardará automáticamente**
4. **Prueba los demás endpoints**

## 🔧 Configuración Avanzada

### Variables de Entorno Personalizadas
```json
{
  "base_url": "https://tu-api-produccion.com",
  "auth_token": "",
  "user_email": "tu-email@ejemplo.com",
  "user_password": "tu-password"
}
```

### Scripts de Automatización
Agrega estos scripts según tus necesidades:

#### Login Automático
```javascript
// En Tests de Login
if (pm.response.code === 200) {
    const responseJson = pm.response.json();
    if (responseJson.token) {
        pm.environment.set("auth_token", responseJson.token);
        console.log("Token guardado:", responseJson.token);
    }
}
```

#### Validación de Respuestas
```javascript
// En Tests de cualquier endpoint
pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Response time is less than 2000ms", function () {
    pm.expect(pm.response.responseTime).to.be.below(2000);
});
```

## 📱 Importar desde Móvil (Postman Mobile)

1. **Abre Postman Mobile**
2. **Tap en "Collections"**
3. **Tap en el "+" para importar**
4. **Selecciona "Import from Files"**
5. **Elige los archivos JSON**
6. **Tap "Import"**

## 🌐 Importar en Postman Web

1. **Ve a web.postman.com**
2. **Click "Import"**
3. **Arrastra los archivos** o selecciónalos
4. **Click "Import"**
5. **Configura el entorno** como en la versión desktop

## 🔄 Actualizar Colección Existente

Si ya tienes una versión anterior:

1. **Exporta tu colección actual** (por seguridad)
2. **Elimina la colección antigua**
3. **Importa la nueva versión**
4. **Configura las variables nuevamente**

## ❌ Solución de Problemas

### Error: "Invalid JSON format"
```
Solución: Verifica que los archivos no estén corruptos
```

### Error: "Collection not found"
```
Solución: Asegúrate de importar ambos archivos (collection y environment)
```

### Error: "Environment variables not working"
```
Solución: Selecciona el entorno correcto en el dropdown
```

### Error: "Scripts not executing"
```
Solución: Verifica que copiaste el script completo en Tests
```

## ✅ Verificación Post-Importación

Después de importar, verifica que tengas:

- [ ] ✅ Colección "API Biblioteca - Colección Completa" visible
- [ ] ✅ Entorno "API Biblioteca - Entorno Local" disponible
- [ ] ✅ 4 carpetas principales (Autenticación, Libros, Usuarios, Préstamos)
- [ ] ✅ Variables `base_url` y `auth_token` configuradas
- [ ] ✅ Scripts de login automático funcionando

## 🎯 Próximos Pasos

1. **Ejecuta la aplicación** Spring Boot
2. **Haz login** para obtener el token
3. **Explora los endpoints** organizados por categorías
4. **Usa los datos de ejemplo** de `Sample-Data.json`
5. **Personaliza** según tus necesidades

---

## 🆘 ¿Necesitas Ayuda?

Si tienes problemas con la importación:

1. **Revisa** este archivo de instrucciones
2. **Consulta** `README-Postman.md` para uso detallado
3. **Verifica** que la aplicación esté ejecutándose
4. **Comprueba** las credenciales en `Sample-Data.json`

**¡La colección está lista para usar! 🚀📚**
