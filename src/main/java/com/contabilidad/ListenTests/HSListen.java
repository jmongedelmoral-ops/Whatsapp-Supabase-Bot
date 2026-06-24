package com.contabilidad.ListenTests;

import java.io.OutputStream;

//Librerías que parsean el request que llega
import io.javalin.Javalin;
import io.javalin.http.Context;

//Librerías para manejar .env
import io.github.cdimascio.dotenv.Dotenv;



/*
Basicamente la única diferencia en este caso es que usamos Javalin para escuchar en lugar de
ServerSocket, esto por que javalin permite manejar el request de forma automática
*/
public class HSListen {
    public static void main(String[] args) {

        //Declaraciones .env
        Dotenv dotenv = Dotenv.load();

        String verifyToken = dotenv.get("VERIFY_TOKEN");




        //Se crea el Javalin en el mismo puerto de pruebas
        Javalin app = Javalin.create().start(8080);
        
        //el metodo GET permite responder a los Request de tipo GET de cierta forma
        //específicamente el get enviado a Path que será manejado por handle
        app.get("/", (Context ctx) -> {
            // El framework ya parseó la URL y te lo entrega directo
            
            //El tipo de dato que debe ser el handle para el correcto parseo de los datos
            ctx.contentType("text/plain");

            /*De código al objeto:
            Javalin parsea el url y usted puede asignar los valores
            mediante el método queryParam(String variable)
            */
            String mode = ctx.queryParam("hub.mode");
            String challenge = ctx.queryParam("hub.challenge");
            String verify = ctx.queryParam("hub.verify_token");

            /*
            Código actualmente no funcional, supuestamente ocupa menos espacio
            que ctx.result() pero no supe usarlo, 
            */
            try (OutputStream os = ctx.outputStream()) {
                
                if (challenge != null && mode == "subscribe" && verify == verifyToken){
                    os.write(challenge.getBytes());
                }
                
            }
            
            /*
            Esta es la parte que logró que el código funcionase,
            peleaste con esto como 2 horas y media por que
            estabas igualando usando el operador lógico en lugar
            del método equals()
            */
            if (challenge != null && mode.equals("subscribe") && verify.equals("3EZ1bzD17Wc9nyEWOzvRRZ7rCO6_7Z17fwumV5YW55eqXhmpJ")){
                ctx.result(challenge);       
                
            }
        });
    }

    /*
    Usaste el siguiente comando en simbolos del sistema para chequear que resultaba:
    curl -i "https://[ENLACE DE NGROK]/?hub.mode=subscribe&hub.challenge=1025087204&hub.verify_token=[TOKEN DE NGROK]"
    
    */
            
}



