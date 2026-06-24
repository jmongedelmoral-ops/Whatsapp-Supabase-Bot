package com.contabilidad.ListenTests;
//Las clases .net se refieren a internet

import java.net.ServerSocket; //Permite escuchar, manda la información mediante un Socket
import java.net.Socket; //Transporta la información escuchada

//Las clases io se refieren a entradas y salidas (Input/Output)

import java.io.InputStreamReader; //Escucha los impulsos eléctricos y los transforma a datos (caractéres, números, etc...) (Sólo escucha de a uno)
import java.io.BufferedReader; //Escucha los impulsos eléctricos y los tranforma a datos (Transforma más a la vez)


public class LocalListen {
    public static void main(String[] args) {
        int puerto = 8080; /*
        El puerto para los datos de internet es el 80, pero como ese ya está reservado
        para el propio internet de la laptop, se usa 8080 para simularlo
        */

        // 1. CONFIGURACIÓN: Java aparta el puerto 8080 en tu laptop
        try (ServerSocket servidor = new ServerSocket(puerto)) {
        /*Este try es importante, es un "try-with-resources", basicamente, si la clase
        tiene un método para cerrarse, el try lo ejecutará para cerrar el recurso antes de terminar
        de forma que el recurso no se quede abierto, de otra forma debería cerrarse manualmente. */

            System.out.println("[-] Servidor activo. Escuchando en http://localhost:" + puerto);


            while (true) {
                // ====================================================================
                // EL PUNTO DE ESCUCHA EXACTO:
                // Aquí el programa se DETIENE. No avanza a la siguiente línea.
                // Se queda congelado esperando que entres al enlace desde el navegador.
                // ====================================================================
                Socket cliente = servidor.accept(); //Básicamente se queda escuchando en el puerto esperando que llegue un request
                
                System.out.println("[+] ¡Conexión entrante detectada!");


                // 2. LECTURA: Aquí es donde Java empieza a leer lo que llega de la URL

                //InputReader traduce, BufferedReader agrupa y decora
                InputStreamReader isr = new InputStreamReader(cliente.getInputStream());
                BufferedReader lector = new BufferedReader(isr);


                // Leemos la primera línea que envía la URL (La petición HTTP)
                String linea = lector.readLine();
                System.out.println("Detalle recibido de la URL -> " + linea);


                // 3. RESPUESTA RÁPIDA: Le decimos al navegador que todo salió bien
                //.getBytes() vuelve al String en bytes
                cliente.getOutputStream().write("HTTP/1.1 200 OK\r\n\r\n¡JSON Recibido!".getBytes());


                // Se cierra la comunicación con este cliente y el bucle vuelve a accept()
                cliente.close();
            }
        } catch (Exception e) {
            System.out.println("Error en el puerto: " + e.getMessage());
        }
    }
}
