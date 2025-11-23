package com.oleksandr.eventprovider.TicketMaster.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketmasterResponse {

    @JsonProperty("_embedded")
    private EmbeddedData embedded;

    public EmbeddedData getEmbedded() {
        return embedded;
    }

    public void setEmbedded(EmbeddedData embedded) {
        this.embedded = embedded;
    }
}