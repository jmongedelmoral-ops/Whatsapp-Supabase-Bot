
package com.contabilidad.WsSupabase.models.GistsClasses;

import javax.annotation.processing.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Files {

    @SerializedName("reporte.md")
    @Expose
    private GistsPayload reporteMd;

    public GistsPayload getReporteMd() {
        return reporteMd;
    }

    public void setReporteMd(GistsPayload reporteMd) {
        this.reporteMd = reporteMd;
    }

}
