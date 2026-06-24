package com.contabilidad.WsSupabase;

import com.contabilidad.WsSupabase.models.WSPayload;
//Librerias para manejar el JSON que llega
import com.google.gson.Gson;

//Librerías que parsean el request que llega
import io.javalin.Javalin;
import io.javalin.http.Context;

import io.github.cdimascio.dotenv.Dotenv;


/**
 * <h2>Código para probar al bot de whatsapp:</h2>
 *  <p><b>Funciones:</b></p>
 * <ul>
 * <li>Leer JSON que llegan desde whatsapp al puerto 8080.</li>
 * </ul>
 *  <p><b>Condiciones para que llegue JSON a este código:</b></p>
 * <ul>
 * <li>Tiene que estar abierto el puerto 8080 en NGROK.</li>
 * <li>El código tiene que estar corriendo.</li>
 * </ul>
 *  <p>Si se cumplen esas condicion al mandar un mensaje al bot de whatssapp 
 * lo verás en límpio en la consola.</p>
 */

public class WSFrontEndTest {
    public static void main(String[] args) {



        //Se crea el Javalin en el mismo puerto de pruebas
        Javalin app = Javalin.create().start(8080);
        Gson gson = new Gson();
        
        
        //el metodo GET permite responder a los Request de tipo GET de cierta forma
        //específicamente el get enviado a Path que será manejado por handle
        app.post("/", (Context ctx) -> {
            
            String jsonString; 
            jsonString = ctx.body();

            WSPayload JSON = gson.fromJson(jsonString, WSPayload.class);


            //TODO: Acceder a los valores con métodos más robustos.
            System.out.println(JSON.getEntry().get(0).getChanges().get(0).getValue().getMessages().get(0).getText().getbody());

        });
    }
            
}




