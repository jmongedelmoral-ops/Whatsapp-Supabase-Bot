# BOT CONTADOR - Sistema Automatizado de Contabilidad vía WhatsApp

Un bot interactivo de contabilidad diseñado para facilitar el registro financiero de comercios y negocios locales directamente desde WhatsApp. Este proyecto representa mi transición desde los fundamentos académicos de la programación hasta el despliegue de soluciones conectadas a la red y bases de datos en la nube.

---

##  Mi Camino de Aprendizaje (De 0 a WAN)

Este repositorio no solo contiene código; documenta mi evolución técnica durante el periodo vacacional entre segundo y tercer semestre. 
Al iniciar este proyecto, mi conocimiento técnico se limitaba a la lógica local y flujos de ejecución aislados:

* **El Punto de Partida:** Empecé sin saber cómo comunicar mi código con el exterior. Conceptos como **puertos**, redes o protocolos me eran completamente ajenos.
* **Descubriendo la Web:** No conocía la arquitectura **Cliente-Servidor**, el ciclo de vida de un **Request/Response**, ni cómo estructurar o manipular un objeto **JSON** para transportar datos.
* **Evolución de Redes en "U":** Para entender cómo se movían los datos, planeé mi aprendizaje de forma incremental:
    1.  **Iteración Local (U):** Pruebas de comunicación interna y bucles lógicos en mi propia máquina.
    2.  **Entorno LAN:** Conexión y entendimiento del flujo de datos entre dispositivos de una misma red local.
    3.  **Despliegue WAN:** El salto definitivo hacia la internet global, exponiendo servicios y consumiendo APIs del mundo real.

---

##  Tecnologías y Arquitectura

* **Backend:** JAVA para la orquestación lógica del bot.
* **Base de Datos:** **Supabase (PostgreSQL)**, integrada de forma autodidacta para la persistencia, modelado y consulta de datos financieros en tiempo real.
* **Integración de Mensajería:** **WhatsApp Cloud API (Meta)**, configurando Webhooks para flujos bidireccionales.
* **Visualización de Reportes:** Integración con la API de **GitHub Gists** para la generación dinámica de tablas de datos legibles.

---

##  Flujo de Datos e Implementación Técnica

El sistema procesa la información financiera del usuario final siguiendo un flujo lógico estructurado en cuatro fases clave:

### 1. Handshake y Autenticación con Meta
Configuré la infraestructura necesaria para establecer la confianza mutua entre mis servidores locales (usando herramientas de túneles como ngrok para el entorno de desarrollo) y los servidores de Meta, validando tokens de verificación de forma segura.

### 2. Interfaz Dinámica de Usuario
Diseñé el envío de payloads estructurados en formato **JSON** hacia la API de WhatsApp para renderizar componentes interactivos directamente en el chat, tales como **botones de respuesta rápida** e interfaces de comandos amigables, evitando que el usuario tenga que escribir texto plano propenso a errores.

```json
{
          "messaging_product": "whatsapp",
          "recipient_type": "individual",
          "to": <PHONE_NUMBER>,
          "type": "interactive",
          "interactive": {
            "type": "button",
            "header": {
                "type": "image",
                "image": {
                "link": "https://imgs.search.brave.com/1QKBDloArN7kW4IJF9GbVnAN5pW7Fq49Moy3fulqVTI/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9tZWRp/YS5pc3RvY2twaG90/by5jb20vaWQvMTM0/MDU4Mzg5MC92ZWN0/b3Ivc2hvcC1sb2Nh/bC1iYWRnZS5qcGc_/cz02MTJ4NjEyJnc9/MCZrPTIwJmM9QWdi/OGhOZWN3NkNPRkJT/MGtOdG91blBNOUpI/aUY2ZHMwNGdmY19m/cEM2VT0"
                    }
                },
            "body": {
              "text": "Boton para registrar compra"
            },
            "footer": {
              "text": "by Tienda genérica"
            },
            "action": {
              "buttons": [
                {
                  "type": "reply",
                  "reply": {
                    "id": "Boton",
                    "title": "Registrar compra"
                  },
                }
              ]
            }
          }
        }
```
### 3. Procesamiento e Ingesta de Datos
Al recibir los inputs del cliente mediante Webhooks, el backend mapea los datos interactivos y los asocia con estructuras de datos predefinidas (hardcoded templates). 
Posteriormente, se ejecuta una consulta de inserción directa en las tablas relacionales de Supabase.

### 4. Generación Expresa de Reportes
Cuando el comerciante ingresa el balance, el sistema extrae las filas de la base de datos, construye una matriz de texto formateada como una tabla limpia y fácil de leer, y la aloja de forma dinámica a través de un GitHub Gist. 
El bot responde al usuario en segundos enviándole únicamente la URL directa del reporte generado.

### Buenas Prácticas de Seguridad Aprendidas
A raíz del desarrollo, implementé directrices estrictas de seguridad en el repositorio:

Manejo de credenciales y llaves de acceso mediante Variables de Entorno (.env).

Configuración estricta del archivo .gitignore para prevenir la exposición de secretos en entornos públicos de control de versiones.

### Conclusiones de este Proyecto
Este bot es la prueba tangible de mi capacidad para tomar un problema del mundo real (la contabilidad de un negocio local), desglosarlo en requerimientos de software y adquirir de manera completamente autónoma las habilidades necesarias (redes, bases de datos, APIs de nivel empresarial) para llevarlo a cabo con éxito.


