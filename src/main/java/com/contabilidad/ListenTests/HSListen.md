## Webhook Handshake: Verificación de Meta (WhatsApp API)

### Mecánica del Endpoint
- **Método:** GET
- **Protocolo Base:** WebSub (requiere parámetros `hub.mode`, `hub.challenge`, `hub.verify_token`).

> ⚠️ **CRÍTICO:** Las configuraciones del contexto (`ctx.status()` y `ctx.contentType("text/plain")`) NO son retroactivas. Deben ejecutarse estrictamente antes de despachar el contenido (`ctx.result()`), de lo contrario los encabezados HTTP se congelarán y Meta rechazará la validación.

### Casos de Éxito y Errores Aprendidos
1. **Comparación de Tokens:** El token recibido se debe evaluar estrictamente con `.equals()`. El uso de `==` romperá la validación de forma silenciosa debido a la comparación de referencias de objetos en Java.
2. **Normalización de Red (Puntos vs Guiones Bajos):** Los proxies/túneles intermedios tienden a duplicar los parámetros mutando los puntos por guiones bajos (`hub_mode`). Se prioriza la extracción de las variables originales con punto (`hub.mode`) para evitar que el separador de protocolo `HTTP/1.1` corrompa el String al final de la petición.
3. **Visualizar flujo de datos**: Ngrok permite utilizar una interfaz web para consultar los requests, responses y estados.

### Comando de Simulación Local
```

curl -i "http://localhost:port/?hub.mode=subscribe&hub.challenge=test_val&hub.verify_token=verifyToken"
```

