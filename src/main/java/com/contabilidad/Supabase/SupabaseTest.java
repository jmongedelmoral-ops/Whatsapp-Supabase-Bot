package com.contabilidad.Supabase;
//Ingredientes

//Manejo de https, socket, maneja requests
import java.io.IOException;
import java.net.URI;
import java.net.http.*;

import io.github.cdimascio.dotenv.Dotenv;;

public class SupabaseTest {

    public static void main(String[] args) {
        
        //Declaracion .env
        Dotenv dotenv = Dotenv.load();
        String url = dotenv.get("URL");
        String apiKey = dotenv.get("API_KEY_SUPABASE");
        

        // Preparar el JSON (hardcodeado)
        String json = """
                {
                "Monto" : 6,
                "Descripción": "GEOOO"}

                    """; // Usar comillas triples te permite hacer un cuerpo de String

        // Preparar el Request y el Response

        // Request builder
        // Esto dice para donde va el request
        URI uri = URI.create(url);

        // Sección de headers

        //Los headers son el pasaporte del request.
        //Contienen distinta información para darle al cliente y que cumpla
        //Sus funciones

        //Header para el tipo de dato:
        String clave1 = "Content-type";
        //Decimos que es un json
        String valor1 = "application/json";



        //Header de llave API (ESTE ES IMPORTANTÍSIMO en SEGURIDAD):
        //Basicamente permite a Supabase saber que eres tú
        String clave2 = "apikey";
        //Damos la constraseña segura unica
        String valor2 = apiKey;

        

        //Header de autorización en internet (En mi caso ambas son la misma)
        String clave3 = "Authorization";
        String valor3 = "Bearer "+apiKey;


        // Este es el CONSTRUCTOR DEL REQUEST
        HttpRequest.Builder requestBldr = HttpRequest.newBuilder(uri);

        // Se asignan headers
        requestBldr.header(clave1, valor1);
        requestBldr.header(clave2, valor2);
        requestBldr.header(clave3, valor3);

        // Meterle el JSON al CONSTRUCTOR del request (Se mete en el body del request)

        // Aqui definimos que los request tipo POST tendran de cuerpo
        // un json traducido de String a JSON mediante el metodo bodypublishers.ofString
        requestBldr = requestBldr.POST(HttpRequest.BodyPublishers.ofString(json));

        // Creamos el Request de verdad
        HttpRequest request = requestBldr.build();

        // Response handler para ver como manejamos lo que devuelva supabase
        HttpResponse.BodyHandler<String> responseHdlr = HttpResponse.BodyHandlers.ofString();
        // Abrir el Socket

        // Este es un metodo nativo simple en java para abrir el socket
        HttpClient Socket = HttpClient.newHttpClient();

        // Usamos el método .send para enviar el request y recibir el response
        // que sabemos será un String
        try{
            HttpResponse<String> response = Socket.send(request, responseHdlr);
            System.out.println("Ta-da!!, el string que llegó fue: " + response.toString());
            System.out.println(response.statusCode());
            System.out.println(response.body());

        }
        catch(IOException e){
            System.out.println("Error de IO: "+ e.getMessage());
        }
        catch(InterruptedException e){
            System.out.println("Error de Interrupción: "+ e.getMessage());
        }
        
    }

}
