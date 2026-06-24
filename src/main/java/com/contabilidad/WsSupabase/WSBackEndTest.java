package com.contabilidad.WsSupabase;

//Librerias para manejar el JSON que llega
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.contabilidad.WsSupabase.models.WSPayload;
import com.contabilidad.WsSupabase.models.GistsClasses.RootResponse;

//Librerías que parsean el request que llega
import io.javalin.Javalin;
import io.javalin.http.Context;

//Librerías que permiten construir un Request
//Manejo de https, socket, maneja requests
import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import io.github.cdimascio.dotenv.Dotenv;

/**
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * <h2> Código para probar al bot de whatsapp:</h2>
 * 
 * 
 * <b>Funciones:</b>
 * <ul>
 * <li>Envia un JSON al API de whatssapp.</li>
 * <li>El usuario ve un botón y se puede identificar su input.</li>
 * <li>Entonces el código ingresa valores hardcodeados a Supabase.</li>
 * </ul>
 * <p><b>Condiciones para que llegue JSON a este código:</b></p>
 * <ul>
 * <li>Tiene que estar abierto el puerto 8080 en NGROK.</li>
 * <li>El código tiene que estar corriendo.</li>
 * </ul>
 *  <p>Si se cumplen esas condiciones, el usuario verá un botón que al pulsar 
 * le regresará un enlace a Gists con los valores hardcodeados como dato 
 * más recientemente añadido.</p>
 */
public class WSBackEndTest {
  public static void main(String[] args) {
    
  // ----------------------SECCIÓN DE DEFINICIONES---------------------------

    //Declaraciones .env

    Dotenv dotenv = Dotenv.load();
    String phoneNumber = dotenv.get("PHONE_NUMBER");
    String urlMeta = dotenv.get("URL_META");
    
    String apiKeyMeta = dotenv.get("API_KEY_META");

    // El Gson que permite el manejo del json
    Gson gson = new Gson();

    // Json para mostrar botones al usuario en whatsapp
    String jsonBoton = """
                {
          "messaging_product": "whatsapp",
          "recipient_type": "individual",
          "to": "%s",
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
        }""".formatted(phoneNumber);

    // Creacion del request Builder

      // La dirección a la que irá el request
      URI uri = URI.create(urlMeta);

      // Headers necesarios

      // Header para el tipo de dato:
      String claveMeta1 = "Content-Type";
      // Decimos que es un json
      String valorMeta1 = "application/json";

      // Header para Autorización
      String claveMeta2 = "Authorization";
      // Clave para que meta sepa que somos nosotros
      String valorMeta2 = "Bearer "+apiKeyMeta;

      // Request Builder
      HttpRequest.Builder rqstBldrMETA = HttpRequest.newBuilder(uri);

      // Se asignan headers
      rqstBldrMETA.header(claveMeta1, valorMeta1);
      rqstBldrMETA.header(claveMeta2, valorMeta2);
      
      // Meterle el JSON al CONSTRUCTOR del request (Se mete en el body del request)
  
      // Aqui definimos que los request tipo POST tendran de cuerpo
      // un json traducido de String a JSON mediante el metodo bodypublishers.ofString
      rqstBldrMETA = rqstBldrMETA.POST(HttpRequest.BodyPublishers.ofString(jsonBoton, StandardCharsets.UTF_8));


    // Creacion del responde Handler
      // Response handler para ver como manejamos lo que devuelva Whatsapp
      HttpResponse.BodyHandler<String> responseHdlr = HttpResponse.BodyHandlers.ofString();


    // Creamos el Request de verdad
    HttpRequest requestMETA = rqstBldrMETA.build();
    
  // ---------------------SECCIÓN DE EJECUCIÓN------------------------

    //Enviar el JSON con el botón al API de META
      
      // Este es un metodo nativo simple en java para abrir el socket
      HttpClient Socket = HttpClient.newHttpClient();

      // Usamos el método .send para enviar el request y recibir el response
      // que sabemos será un String
      try {
        HttpResponse<String> response = Socket.send(requestMETA, responseHdlr);
        System.out.println("El botón fue enviado " + response.toString());
        System.out.println(response.statusCode());
        System.out.println(response.body());

      } catch (IOException e) {
        System.out.println("Error de IO: " + e.getMessage());
      } catch (InterruptedException e) {
        System.out.println("Error de Interrupción: " + e.getMessage());
      }


    // Escuchar input requests del API (Mensajes recibidos, botón pulsado)

      // Se crea el Javalin en el mismo puerto de pruebas
      Javalin app = Javalin.create().start(8080);

      // Escuchamos las peticiones de tipo POST
      app.post("/", (Context ctx) -> {

        // Atrapamos el JSON en un String para poder manejarlo
        String jsonString;
        jsonString = ctx.body();

        // Se parsea el string para convertirlo en la clase espejo WSPayLoad, por lo que es fácil acceder a sus valores.
        WSPayload JSON = gson.fromJson(jsonString, WSPayload.class);

        // Si el JSON no tiene mensajes, se ignora para facilitar la lectura al mantener la consola límpia.
        if (JSON.getEntry().get(0).getChanges().get(0).getValue().getMessages() == null) {

          System.out.println("Irrelevante");
        } else {


          /*
          Se usa la siguiente estructura que asume un formato ideal
          para acceder a los valores del JSON en función de priorizar el funcionamiento
          antes que la escalabilidad

          RIESGO: En el caso de que META cambie el JSON, soltará un 'NullPointerException'
          e ignora todo lo que no sea estrictamente de tipo texto.

          TODO: En la versión final se optará por un mapeo de datos más robusto.


          */

          //TRUE: Si el usuario manda un mensaje de texto al bot
          if (JSON.getEntry().get(0).getChanges().get(0).getValue().getMessages().get(0).getType().equals("text")) {
            String mensaje = JSON.getEntry().get(0).getChanges().get(0).getValue().getMessages().get(0).getText()
                .getbody();
            System.out.println("El usuario ha mandado un mensaje: " + mensaje);
          } 
          

          //TRUE: Si el usuario presiona el botón del bot
          else if (JSON.getEntry().get(0).getChanges().get(0).getValue().getMessages().get(0).getType()
              .equals("interactive")
              && JSON.getEntry().get(0).getChanges().get(0).getValue().getMessages().get(0).getInteractive() != null) {
            String botonPulsado = JSON.getEntry().get(0).getChanges().get(0).getValue().getMessages().get(0)
                .getInteractive().getButtonReply().getId();
            System.out.println("El usuario pulsó un botón, específicamente: " + botonPulsado);
            

            //Los datos hardcodeados en la función se suben a supabase.
            int confirmar = subirDatoASupabase();
            

            //TRUE: Los datos se subieron correctamente a Supabase
            if (confirmar > 100 && confirmar < 300) {
              consultarDatosSupabase();

            }
            //TO DO: Implementar un sistema de response que avise al usuario que el dato no se ha subido correctamente.
            else
              System.out.println("No se subieron los datos a supabase");

          }
        }

      });

    

    }
  
  //----------------------------------------MÉTODOS--------------------------------------------

  //No toma inputs, devuelve el statusCode del request.
  public static int subirDatoASupabase() {

    //Declaraciones .env
    Dotenv dotenv = Dotenv.load();

    String urlSpbs = dotenv.get("URL_SUPABASE");
    String apiKeySpbs = dotenv.get("API_KEY_SUPABASE");

    // Preparar el JSON (hardcodeado)
    String json = """
        {
        "Monto" : 53,
        "Descripción": "057E"}

            """; 

    // Preparar el Request y el Response

    // Request builder

      // Esto dice para donde va el request
      URI url = URI.create(urlSpbs);

      // Sección de headers

      // Header para el tipo de dato:
      String claveSpbs1 = "Content-type";
      // Decimos que es un json
      String valorSpbs1 = "application/json";

      // Header de llave API (ESTE ES IMPORTANTÍSIMO en SEGURIDAD):
      // Basicamente permite a Supabase saber que eres tú
      String claveSpbs2 = "apikey";
      // Damos la constraseña segura unica
      String valorSpbs2 = apiKeySpbs;

      // Header de autorización en internet (En mi caso ambas son la misma)
      String claveSpbs3 = "Authorization";
      String valorSpbs3 = "Bearer "+apiKeySpbs;

      // Este es el CONSTRUCTOR DEL REQUEST
      HttpRequest.Builder rqstBldrSpbs = HttpRequest.newBuilder(url);

      // Se asignan headers
      rqstBldrSpbs.header(claveSpbs1, valorSpbs1);
      rqstBldrSpbs.header(claveSpbs2, valorSpbs2);
      rqstBldrSpbs.header(claveSpbs3, valorSpbs3);

      // Aqui definimos que los request tipo POST tendran de cuerpo
      // un json traducido de String a JSON mediante el metodo bodypublishers.ofString
      rqstBldrSpbs = rqstBldrSpbs.POST(HttpRequest.BodyPublishers.ofString(json));

      // Creamos el Request de verdad
      HttpRequest request = rqstBldrSpbs.build();

    // Response handler para ver como manejamos lo que devuelva supabase
    HttpResponse.BodyHandler<String> responseHdlr = HttpResponse.BodyHandlers.ofString();

    //Subir dato a supabase

      // Este es un metodo nativo simple en java para abrir el socket
      HttpClient Socket = HttpClient.newHttpClient();

      // Usamos el método .send para enviar el request y recibir el response
      // que sabemos será un String
      try {
        HttpResponse<String> response = Socket.send(request, responseHdlr);
        System.out.println("Se intentó subir el dato a supabase: " + response.toString());
        System.out.println(response.statusCode());
        System.out.println(response.body());
        return response.statusCode();

      } catch (IOException e) {
        System.out.println("Error de IO: " + e.getMessage());
        return 0;
      } catch (InterruptedException e) {
        System.out.println("Error de Interrupción: " + e.getMessage());
        return 0;
      }

  }

  //Realiza un request de tipo GET al enlace a supabase, un tipo POST a GIST y un POST al api de meta con el url de gists
  //Devuelve el status code del GET request a supabase
  public static int consultarDatosSupabase() {

  //--------------------------DEFINICIÓN DE VARIABLES----------------------
    //Declaraciones .env
    Dotenv dotenv = Dotenv.load();

    String urlSpbs = dotenv.get("URL_SUPABASE");
    String urlGst = dotenv.get("URL_GISTS");

    String apiKeySpbs = dotenv.get("API_KEY_SUPABASE");
    String apiKeyGst = dotenv.get("API_KEY_GISTS");


    // Request builder
    // Esto dice para donde va el request
    // Url supabase
    URI uriSpbs = URI.create(urlSpbs);
    URI uriGst = URI.create(urlGst);

    // Sección de headers Supabase

    // Los headers son el pasaporte del request.
    // Contienen distinta información para darle al cliente y que cumpla
    // Sus funciones

    // Header para el tipo de dato:
    String claveSpbs1 = "Content-type";
    // Decimos que es un json
    String valorSpbs1 = "application/json";

    // Header de llave API (ESTE ES IMPORTANTÍSIMO en SEGURIDAD):
    // Basicamente permite a Supabase saber que eres tú
    String claveSpbs2 = "apikey";
    // Damos la constraseña segura unica
    String valorSpbs2 = apiKeySpbs;

    // Header de autorización en internet (En mi caso ambas son la misma)
    String claveSpbs3 = "Authorization";
    String valorSpbs3 = "Bearer "+ apiKeySpbs;

    // Sección de headers gists

      // Header para el tipo de dato
      String claveGst1 = "Accept";
      String valorGst1 = "application/vnd.github+json";

      // Header de authorization
      String claveGst2 = "Authorization";
      String valorGst2 = "Bearer "+apiKeyGst;

      // Header de versión de API
      String claveGst3 = "X-GitHub-Api-Version";
      String valorGst3 = "2026-03-10";

    // Este es el CONSTRUCTOR DEL REQUEST
    // Para el supabase
    HttpRequest.Builder rqstBldrSpbs = HttpRequest.newBuilder(uriSpbs);

    // Para gists (crear la tabla)
    HttpRequest.Builder rqstBldrGst = HttpRequest.newBuilder(uriGst);

    // Se asignan headers

    // Supabase headers
    rqstBldrSpbs.header(claveSpbs1, valorSpbs1);
    rqstBldrSpbs.header(claveSpbs2, valorSpbs2);
    rqstBldrSpbs.header(claveSpbs3, valorSpbs3);

    // Gists headers
    rqstBldrGst.header(claveGst1, valorGst1);
    rqstBldrGst.header(claveGst2, valorGst2);
    rqstBldrGst.header(claveGst3, valorGst3);

    // El request de Supabase será un GET esta vez para consultar la base de datos

    rqstBldrSpbs = rqstBldrSpbs.GET();

    // Creamos el Request de verdad
    HttpRequest rqstSpbs = rqstBldrSpbs.build();
  
    // Response handler para ver como manejamos lo que devuelva supabase
    HttpResponse.BodyHandler<String> responseHdlr = HttpResponse.BodyHandlers.ofString();
    // Abrir el Socket
    
  //--------------------------------EJECUCIÓN--------------------------------

    //Solicitar datos a SUPABASE
      // Este es un metodo nativo simple en java para abrir el socket
      HttpClient Socket = HttpClient.newHttpClient();

      // Usamos el método .send para enviar el request y recibir el response
      // que sabemos será un String
      try {
        HttpResponse<String> response = Socket.send(rqstSpbs, responseHdlr);
        System.out.println("Se han solicitados los datos a Supabase " + response.toString());
        System.out.println(response.statusCode());

        // Obtenemos el rpnsBody que debe dar un Arreglo con los datos de la tabla
        String rpnsBodyGetSpbs = response.body();
        System.out.println(rpnsBodyGetSpbs);

        // Volver el response de String a Lista<String>

        String rpnsMapped = prepararJsonParaGist(rpnsBodyGetSpbs);
        System.out.println("Mapeo: " + rpnsMapped);


    //Enviar crea GISTS con los datos

      // Creamos el constructor del request tipo POST con el JSON

      rqstBldrGst = rqstBldrGst.method("PATCH",HttpRequest.BodyPublishers.ofString(rpnsMapped.toString()));
      // Creamos el Request para crear un Gists

      HttpRequest rqstGst = rqstBldrGst.build();

      // Enviamos el request por el socket
      HttpResponse<String> rpnsGst = Socket.send(rqstGst, responseHdlr);

      // ATENCIÓN: CADA GISTS CREADO ES PERMANENTE.
      // TO DO: HACER ALGO PARA NO LLENARTE DE GISTS
      System.out.println("Se han enviado los datos a Gists: " + rpnsGst.toString());
      System.out.println(rpnsGst.statusCode());
      String rpnsBodyGetGst = rpnsGst.body();
      System.out.println(rpnsBodyGetGst);

      Gson gson = new Gson();

      RootResponse JSONResponse = gson.fromJson(rpnsBodyGetGst, RootResponse.class);
      String htmlString = JSONResponse.getHtmlUrl();

      mandarURLAlUsuario(htmlString);

      return response.statusCode();

    } catch (IOException e) {
      System.out.println("Error de IO: " + e.getMessage());
      return 0;
    } catch (InterruptedException e) {
      System.out.println("Error de Interrupción: " + e.getMessage());
      return 0;
    }

  }

  // Método que manda el URL al usuario
  public static int mandarURLAlUsuario(String urlHtml) {

    // Json para mostrar botones al usuario en whatsapp

    String jsonUrl = construirJsonDelUrl(urlHtml);

    // Creacion del request Builder

    // La dirección a la que irá el request
    URI uri = URI.create("https://graph.facebook.com/v25.0/1196398723546100/messages");

    // Headers necesarios

    // Header para el tipo de dato:
    String claveMeta1 = "Content-Type";
    // Decimos que es un json
    String valorMeta1 = "application/json";

    // Header para Autorización
    String claveMeta2 = "Authorization";
    // Clave para que meta sepa que somos nosotros
    String valorMeta2 = "Bearer EAA8RZCYAC1gcBRgzLSuRD93yus9Go8AHZCZCZAB1tZAZBJIkx0wS5pmVbdvbgXvxnCMZBJnlBoYt5W6fXANMsZAMKrDFuRUZBXULbS6SqwLF5ngBQrLiL6FE6MXyH0i0JM2B5tBlCyNXYxcYuBGOo3ylHWbGKjD8mcv61hDLogXg6gLTuJb1sXXKwgZAAwtJ7wyQZDZD";

    // Request Builder
    HttpRequest.Builder rqstBldrSpbs = HttpRequest.newBuilder(uri);

    // Se asignan headers
    rqstBldrSpbs.header(claveMeta1, valorMeta1);
    rqstBldrSpbs.header(claveMeta2, valorMeta2);

    // Meterle el JSON al CONSTRUCTOR del request (Se mete en el body del request)

    // Aqui definimos que los request tipo POST tendran de cuerpo
    // un json traducido de String a JSON mediante el metodo bodypublishers.ofString
    rqstBldrSpbs = rqstBldrSpbs.POST(HttpRequest.BodyPublishers.ofString(jsonUrl, StandardCharsets.UTF_8));

    // Creamos el Request de verdad
    HttpRequest request = rqstBldrSpbs.build();
    // Response handler para ver como manejamos lo que devuelva Whatsapp

    HttpResponse.BodyHandler<String> responseHdlrURL = HttpResponse.BodyHandlers.ofString();
    // Abrir el Socket

    // Este es un metodo nativo simple en java para abrir el socket
    HttpClient Socket = HttpClient.newHttpClient();

    // Usamos el método .send para enviar el request y recibir el response
    // que sabemos será un String
    try {
      HttpResponse<String> response = Socket.send(request, responseHdlrURL);
      System.out.println("Se intentó enviar el url al usuario: " + response.toString());
      System.out.println(response.statusCode());
      System.out.println(response.body());
      return response.statusCode();

    } catch (IOException e) {
      System.out.println("Error de IO: " + e.getMessage());
      return 0;
    } catch (InterruptedException e) {
      System.out.println("Error de Interrupción: " + e.getMessage());
      return 0;
    }

  }

  // Método que Mapea Strings y te los da en tabla (Sacado de gémini)
  // Devuelve un Json de gists con contenido parseado como tabla
  public static String prepararJsonParaGist(String jsonSupabase) {
    // 1. Crear los encabezados de la tabla Markdown
    StringBuilder markdown = new StringBuilder();
    markdown.append("| Descripción | Monto |\n");
    markdown.append("| ----------- | ----- |\n");

    // 2. Parsear el String crudo de Supabase directamente a un arreglo iterable
    JsonArray listaRegistros = JsonParser.parseString(jsonSupabase).getAsJsonArray();

    // 3. El bucle recorre de forma automática CADA elemento de la lista sin
    // importar cuántos sean
    for (JsonElement elemento : listaRegistros) {
      JsonObject registro = elemento.getAsJsonObject();

      // Extraer dinámicamente los valores usando las llaves del JSON
      String descripcion = registro.get("Descripción").getAsString();
      String monto = registro.get("Monto").getAsString();

      // Agregar la fila formateada al StringBuilder
      markdown.append("| ").append(descripcion)
          .append(" | $").append(monto)
          .append(" | \n");
    }

    // 4. Construir el árbol JSON para la API de Gists usando Gson
    JsonObject jsonPrincipal = new JsonObject();
    jsonPrincipal.addProperty("description", "Reporte Automático de Contabilidad");

    JsonObject filesNode = new JsonObject();
    JsonObject fileContentNode = new JsonObject();

    // Inyectar el texto Markdown generado dinámicamente
    fileContentNode.addProperty("content", markdown.toString());

    // Definir el nombre del archivo con extensión .md para su correcto renderizado
    filesNode.add("reporte.md", fileContentNode);
    jsonPrincipal.add("files", filesNode);

    // 5. Retornar la cadena JSON serializada y escapada correctamente
    return jsonPrincipal.toString();
  }

  public static String construirJsonDelUrl(String gistUrl) {

    //Declaraciones .env
    
    Dotenv dotenv = Dotenv.load();
    String phoneNumber = dotenv.get("PHONE_NUMBER");

    // 1. Creamos la caja interna ("text")
    JsonObject textObject = new JsonObject();
    textObject.addProperty("preview_url", true); // Hace que WhatsApp intente mostrar una vista previa del link
    textObject.addProperty("body", "Aquí tienes tu reporte de contabilidad: " + gistUrl);

    // 2. Creamos la caja principal (Raíz del JSON)
    JsonObject rootObject = new JsonObject();
    rootObject.addProperty("messaging_product", "whatsapp");
    rootObject.addProperty("recipient_type", "individual");
    rootObject.addProperty("to", phoneNumber);
    rootObject.addProperty("type", "text");

    // 3. Metemos la caja interna dentro de la principal
    rootObject.add("text", textObject);

    // 4. Convertimos todo el árbol a String en una sola línea
    return new Gson().toJson(rootObject);
  }

}
