
package com.contabilidad.WsSupabase.models.GistsClasses;

import javax.annotation.processing.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * <h2>Modelo de Respuesta de GitHub Gists </h2>
 * * <p>Esta clase y sus estructuras secundarias representan el Data Transfer Object (DTO)
 * utilizado para deserializar la respuesta de la API de GitHub tras crear o actualizar un Gist.</p>
 * * <p><b>Nota de Arquitectura:</b> Debido a la alta densidad y profundidad del esquema JSON de GitHub,
 * esta estructura fue <i>autogenerada mediante ingeniería inversa de software (Json-to-Pojo)</i> 
 * para acelerar el tiempo de desarrollo y garantizar el acoplamiento exacto con los tipos de la API.</p>
 * * <p><b>Pragmatismo de Diseño:</b> Aunque el Payload de GitHub entrega decenas de metadatos (fechas, sub-objetos del owner, estadísticas),
 * el propósito exclusivo de este módulo en la Prueba de Concepto (PoC) es aislar y extraer de forma segura el 
 * parámetro <code>html_url</code> (URL público del Gist) para ser despachado inmediatamente hacia el cliente vía WhatsApp.</p>
 * * @see <a href="https://docs.github.com/en/rest/gists/gists">GitHub Gists API Reference</a>
 */

@Generated("jsonschema2pojo")
public class GistsPayload {

    @SerializedName("filename")
    @Expose
    private String filename;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("raw_url")
    @Expose
    private String rawUrl;
    @SerializedName("size")
    @Expose
    private int size;
    @SerializedName("truncated")
    @Expose
    private boolean truncated;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("encoding")
    @Expose
    private String encoding;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getRawUrl() {
        return rawUrl;
    }

    public void setRawUrl(String rawUrl) {
        this.rawUrl = rawUrl;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isTruncated() {
        return truncated;
    }

    public void setTruncated(boolean truncated) {
        this.truncated = truncated;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

}
