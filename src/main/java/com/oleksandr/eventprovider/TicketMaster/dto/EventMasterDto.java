package com.oleksandr.eventprovider.TicketMaster.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventMasterDto {

    private String id;
    private String name;
    private List<ImageDto> images;
    private DatesDto dates;

    @JsonProperty("_embedded") // Внутри Event тоже есть "_embedded" для Venues
    private EmbeddedData eventEmbedded;

}