Resumen de la Implementación

## 🚀 Flujo de Conexión y Persistencia de Datos
Se implementó una arquitectura de comunicación cliente-servidor utilizando Java HttpClient para interactuar con la API REST de Supabase (PostgreSQL).

- **Petición (Request):** Construida mediante HttpRequest.Builder, empaquetando el cuerpo (JSON) y las credenciales necesarias.

- **Gestión de Respuesta (Response)**: Utilización de HttpResponse.BodyHandlers.ofString() para procesar la confirmación del servidor.

- **Capa de Seguridad (RLS - Row Level Security):**

- **Autenticación:** Validación mediante apikey y Authorization: Bearer en los headers de la petición.

- **Autorización:** Configuración del rol anon en la tabla Transacciones mediante una política personalizada de INSERT, permitiendo la escritura externa bajo el criterio WITH CHECK: true.

## 🐞 Ciclo de Depuración (Lecciones aprendidas)
El proceso de integración permitió identificar y resolver errores mediante los códigos de estado HTTP:

>**404 Not Found:** Corrección de la URL de destino al especificar la ruta /rest/v1/Tabla.

>**400 Bad Request:** Ajuste en la estructura del JSON para cumplir con el esquema de la base de datos.

>**401 Unauthorized:** Verificación de las llaves de acceso (anon) y el formato correcto del header Authorization.

>**Error de Política (RLS):** Modificación del "guardián" de la base de datos para habilitar permisos de INSERT al rol anon.

TODO:
- Entender el authorization Bearer