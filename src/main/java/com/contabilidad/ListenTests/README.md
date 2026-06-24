# Proyecto: Mi Primer Bot de WhatsApp 

Este documento es para mi "yo del futuro". Aquí está el conocimiento de redes que adquirí desarmando la arquitectura cliente-servidor desde cero en mis vacaciones de verano.

## Conceptos Clave Dominados

### 1. El Puerto 8080 e InetAddress
* **IP vs Puerto:** La dirección IP localiza la computadora en el mundo (el edificio). El puerto localiza la aplicación exacta (el departamento/oficina). El puerto `8080` es mi oficina de desarrollo.
* **InetAddress:** Es la clase de Java que encapsula las IPs y dominios. No usa constructor `new`, sino métodos estáticos de fábrica como `.getByName()`.
* **La IP 0.0.0.0:** No es un destino para conectar. Es una meta-dirección de escucha que le dice al servidor: *"Escucha peticiones de todas mis tarjetas de red (Wi-Fi, Ethernet y Local)"*.

### 2. Tipos de Comunicación y su Alcance
* **Comunicación U (Loopback):** Tráfico interno en `127.0.0.1` o `localhost`. El cartero no sale de la máquina.
* **Comunicación LAN (Local Area Network):** Conexión en la misma red Wi-Fi (ej. Celular conectado a la laptop mediante la IP privada `192.168.x.x`).
* **Comunicación WAN (Wide Area Network):** El internet de verdad. Tráfico global que requiere cruzar routers mediante IPs Públicas.

---

## 🛠️ La Solución de Infraestructura: ngrok
Para probar conexiones WAN sin abrir puertos manualmente en el router ni romper el Firewall de Windows (lo cual expone la laptop), implementé **ngrok**.

### ¿Cómo funciona?
Abre un túnel seguro e inverso desde dentro de mi laptop hacia los servidores en la nube de ngrok utilizando mi **AuthToken** (mi llave VIP secreta). Me genera una URL pública (`https://...`). Cualquier persona en el mundo que entre ahí, cae directamente en mi puerto `8080`.

### Comandos Clave en Consola:
```bash
# 1. Autenticar mi máquina (Solo se hace la primera vez)
ngrok config add-authtoken MI_TOKEN_SECRETO

# 2. Levantar el túnel HTTP en el puerto de mi servidor Java
ngrok http 8080

TO DO:

//Datos del bot de whatsapp
+1 (555) 646-4780
Phone Number ID:
1052114394662742
WhatsApp Business Account ID:
1542555170594198
