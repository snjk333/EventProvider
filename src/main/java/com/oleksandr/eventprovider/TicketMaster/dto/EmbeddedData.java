package com.oleksandr.eventprovider.TicketMaster.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmbeddedData {

    private List<EventMasterDto> events;

    public List<EventMasterDto> getEvents() {
        return events;
    }

    public void setEvents(List<EventMasterDto> events) {
        this.events = events;
    }
}