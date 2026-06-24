package com.contabilidad.ListenTests;
//Las clases .net se refieren a internet

import java.net.InetAddress;
import java.net.ServerSocket; //Permite escuchar, manda la información mediante un Socket
import java.net.Socket; //Transporta la información escuchada

//Las clases io se refieren a entradas y salidas (Input/Output)

import java.io.InputStreamReader; //Escucha los impulsos eléctricos y los transforma a datos (caractéres, números, etc...) (Sólo escucha de a uno)
import java.io.BufferedReader; //Escucha los impulsos eléctricos y los tranforma a datos (Transforma más a la vez)

import io.github.cdimascio.dotenv.Dotenv;

//Basicamente la única diferencia en este caso es que 
public class LANListen {
    public static void main(String[] args) {

        //Declaraciones .env
        Dotenv dotenv = Dotenv.load();

        String ipEscucha = dotenv.get("IP_ESCUCHA");



        // 1. CONFIGURACIÓN: Java aparta el puerto 8080 en tu laptop
        try (ServerSocket servidor = new ServerSocket(8080, 50,InetAddress.getByName("0.0.0.0"));) {
            /*
             * Este try es importante, es un "try-with-resources", basicamente, si la clase
             * tiene un método para cerrarse, el try lo ejecutará para cerrar el recurso
             * antes de terminar
             * de forma que el recurso no se quede abierto, de otra forma debería cerrarse
             * manualmente.
             */

            System.out.println("[-] Servidor activo. Escuchando en http://"+ipEscucha+":" + servidor.getLocalPort());

            while (true) {
                // ====================================================================
                // EL PUNTO DE ESCUCHA EXACTO:
                // Aquí el programa se DETIENE. No avanza a la siguiente línea.
                // Se queda congelado esperando que entres al enlace desde el navegador.
                // ====================================================================
                Socket cliente = servidor.accept(); // Básicamente se queda escuchando en el puerto esperando que llegue
                                                    // un request

                System.out.println("[+] ¡Conexión entrante detectada!");

                // 2. LECTURA: Aquí es donde Java empieza a leer lo que llega de la URL

                // InputReader traduce, BufferedReader agrupa y decora
                InputStreamReader isr = new InputStreamReader(cliente.getInputStream());
                BufferedReader lector = new BufferedReader(isr);

                // Leemos la primera línea que envía la URL (La petición HTTP)
                String linea = lector.readLine();
                System.out.println("Detalle recibido de la URL -> " + linea );


                // 3. RESPUESTA RÁPIDA: Le decimos al navegador que todo salió bien
                // .getBytes() vuelve al String en bytes
                cliente.getOutputStream().write("HTTP/1.1 200 OK\r\n\r\n Hola".getBytes());

                // Se cierra la comunicación con este cliente y el bucle vuelve a accept()
                cliente.close();
            }
        } catch (Exception e) {
            System.out.println("Error en el puerto: " + e.getMessage());
        }
    }
}
