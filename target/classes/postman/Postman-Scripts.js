// ========================================
// SCRIPTS PARA POSTMAN - API BIBLIOTECA
// ========================================

// ========================================
// SCRIPT PARA LOGIN AUTOMÁTICO
// ========================================
// Agregar este script en el evento "Tests" de las peticiones de Login

// Verificar que la respuesta sea exitosa
pm.test("Login exitoso", function () {
    pm.response.to.have.status(200);
});

// Extraer y guardar el token automáticamente
if (pm.response.code === 200) {
    const responseJson = pm.response.json();
    
    if (responseJson.token) {
        // Guardar el token en la variable de entorno
        pm.environment.set("auth_token", responseJson.token);
        
        // Guardar información del usuario
        pm.environment.set("user_name", responseJson.nombre);
        pm.environment.set("user_email", responseJson.email);
        pm.environment.set("user_type", responseJson.tipoUsuario);
        
        console.log("✅ Login exitoso!");
        console.log("👤 Usuario:", responseJson.nombre, responseJson.apellido);
        console.log("📧 Email:", responseJson.email);
        console.log("🔑 Token guardado:", responseJson.token);
        console.log("👨‍💼 Tipo:", responseJson.tipoUsuario);
    } else if (responseJson.mensaje) {
        console.log("❌ Error en login:", responseJson.mensaje);
    }
} else {
    console.log("❌ Error HTTP:", pm.response.code);
}

// ========================================
// SCRIPT PARA VALIDAR RESPUESTAS DE LIBROS
// ========================================
// Agregar este script en el evento "Tests" de las peticiones de libros

pm.test("Respuesta exitosa", function () {
    pm.expect(pm.response.code).to.be.oneOf([200, 201, 204]);
});

pm.test("Tiempo de respuesta aceptable", function () {
    pm.expect(pm.response.responseTime).to.be.below(2000);
});

// Validar estructura de libro individual
if (pm.response.code === 200 && pm.response.json().id) {
    const libro = pm.response.json();
    
    pm.test("Libro tiene campos requeridos", function () {
        pm.expect(libro).to.have.property('id');
        pm.expect(libro).to.have.property('titulo');
        pm.expect(libro).to.have.property('autor');
        pm.expect(libro).to.have.property('isbn');
        pm.expect(libro).to.have.property('anioPublicacion');
        pm.expect(libro).to.have.property('estado');
    });
    
    pm.test("Campos tienen tipos correctos", function () {
        pm.expect(libro.id).to.be.a('number');
        pm.expect(libro.titulo).to.be.a('string');
        pm.expect(libro.autor).to.be.a('string');
        pm.expect(libro.isbn).to.be.a('string');
        pm.expect(libro.anioPublicacion).to.be.a('number');
        pm.expect(libro.estado).to.be.a('string');
    });
}

// Validar lista de libros
if (pm.response.code === 200 && Array.isArray(pm.response.json())) {
    const libros = pm.response.json();
    
    pm.test("Lista de libros no está vacía", function () {
        pm.expect(libros.length).to.be.greaterThan(0);
    });
    
    pm.test("Cada libro tiene ID", function () {
        libros.forEach((libro, index) => {
            pm.expect(libro, `Libro ${index}`).to.have.property('id');
        });
    });
}

// ========================================
// SCRIPT PARA VALIDAR RESPUESTAS DE USUARIOS
// ========================================
// Agregar este script en el evento "Tests" de las peticiones de usuarios

pm.test("Respuesta exitosa", function () {
    pm.expect(pm.response.code).to.be.oneOf([200, 201, 204]);
});

// Validar estructura de usuario individual
if (pm.response.code === 200 && pm.response.json().id) {
    const usuario = pm.response.json();
    
    pm.test("Usuario tiene campos requeridos", function () {
        pm.expect(usuario).to.have.property('id');
        pm.expect(usuario).to.have.property('nombre');
        pm.expect(usuario).to.have.property('apellido');
        pm.expect(usuario).to.have.property('email');
        pm.expect(usuario).to.have.property('tipoUsuario');
        pm.expect(usuario).to.have.property('estado');
    });
    
    pm.test("Email tiene formato válido", function () {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        pm.expect(usuario.email).to.match(emailRegex);
    });
}

// ========================================
// SCRIPT PARA VALIDAR RESPUESTAS DE PRÉSTAMOS
// ========================================
// Agregar este script en el evento "Tests" de las peticiones de préstamos

pm.test("Respuesta exitosa", function () {
    pm.expect(pm.response.code).to.be.oneOf([200, 201, 204]);
});

// Validar estructura de préstamo individual
if (pm.response.code === 200 && pm.response.json().id) {
    const prestamo = pm.response.json();
    
    pm.test("Préstamo tiene campos requeridos", function () {
        pm.expect(prestamo).to.have.property('id');
        pm.expect(prestamo).to.have.property('usuario');
        pm.expect(prestamo).to.have.property('libro');
        pm.expect(prestamo).to.have.property('fechaPrestamo');
        pm.expect(prestamo).to.have.property('estado');
    });
    
    pm.test("Fechas tienen formato correcto", function () {
        pm.expect(prestamo.fechaPrestamo).to.match(/^\d{4}-\d{2}-\d{2}$/);
        if (prestamo.fechaDevolucionEsperada) {
            pm.expect(prestamo.fechaDevolucionEsperada).to.match(/^\d{4}-\d{2}-\d{2}$/);
        }
    });
}

// ========================================
// SCRIPT PARA VALIDAR ERRORES
// ========================================
// Agregar este script en peticiones que esperan errores

pm.test("Error esperado", function () {
    pm.expect(pm.response.code).to.be.oneOf([400, 401, 403, 404, 500]);
});

pm.test("Mensaje de error presente", function () {
    if (pm.response.code >= 400) {
        const responseText = pm.response.text();
        pm.expect(responseText).to.not.be.empty;
    }
});

// ========================================
// SCRIPT PARA ESTADÍSTICAS
// ========================================
// Agregar este script en peticiones de estadísticas

pm.test("Estadísticas válidas", function () {
    pm.expect(pm.response.code).to.equal(200);
    
    const stats = pm.response.json();
    pm.expect(stats).to.be.an('object');
    
    // Verificar que los valores son números
    Object.values(stats).forEach(value => {
        pm.expect(value).to.be.a('number');
        pm.expect(value).to.be.at.least(0);
    });
    
    console.log("📊 Estadísticas:", JSON.stringify(stats, null, 2));
});

// ========================================
// SCRIPT PARA BÚSQUEDAS
// ========================================
// Agregar este script en peticiones de búsqueda

pm.test("Búsqueda exitosa", function () {
    pm.expect(pm.response.code).to.equal(200);
});

pm.test("Resultados son array", function () {
    const results = pm.response.json();
    pm.expect(results).to.be.an('array');
});

// ========================================
// UTILIDADES GENERALES
// ========================================

// Función para imprimir respuesta en consola
function logResponse() {
    console.log("📡 Response Status:", pm.response.code);
    console.log("⏱️ Response Time:", pm.response.responseTime + "ms");
    
    if (pm.response.code === 200) {
        const data = pm.response.json();
        console.log("📄 Response Data:", JSON.stringify(data, null, 2));
    } else {
        console.log("❌ Response Error:", pm.response.text());
    }
}

// Función para validar token
function validateToken() {
    const token = pm.environment.get("auth_token");
    if (!token || token === "") {
        console.log("⚠️ No hay token de autenticación configurado");
        return false;
    }
    return true;
}

// Función para obtener timestamp
function getCurrentTimestamp() {
    return new Date().toISOString();
}

// ========================================
// EJEMPLO DE USO EN PREREQUEST SCRIPT
// ========================================

// Agregar timestamp a las peticiones
pm.request.headers.add({
    key: 'X-Timestamp',
    value: getCurrentTimestamp()
});

// Verificar token antes de peticiones autenticadas
if (pm.request.url.toString().includes('/api/') && 
    !pm.request.url.toString().includes('/auth/login') &&
    !pm.request.url.toString().includes('/auth/register')) {
    
    if (!validateToken()) {
        console.log("❌ Token requerido para esta petición");
    }
}

// ========================================
// INSTRUCCIONES DE USO
// ========================================

/*
INSTRUCCIONES PARA USAR ESTOS SCRIPTS:

1. LOGIN AUTOMÁTICO:
   - Copia el script de "LOGIN AUTOMÁTICO" 
   - Pégalo en la pestaña "Tests" de las peticiones de Login
   - El token se guardará automáticamente

2. VALIDACIÓN DE RESPUESTAS:
   - Copia los scripts correspondientes según el tipo de endpoint
   - Pégalos en la pestaña "Tests" de cada petición
   - Los tests se ejecutarán automáticamente

3. UTILIDADES:
   - Usa las funciones en scripts personalizados
   - Agrega el script de "PREREQUEST" para funcionalidades globales

4. PERSONALIZACIÓN:
   - Modifica los tests según tus necesidades
   - Agrega validaciones específicas para tu API
   - Usa console.log() para debugging

EJEMPLO DE IMPLEMENTACIÓN:

1. Abre la petición "Login Usuario"
2. Ve a la pestaña "Tests"
3. Copia y pega el script "LOGIN AUTOMÁTICO"
4. Guarda la colección
5. Ejecuta la petición
6. Verifica en la consola que el token se guardó

¡Los scripts están listos para usar! 🚀
*/
