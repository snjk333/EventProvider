package com.oleksandr.eventprovider.TicketMaster;

import com.oleksandr.eventprovider.Event.Event;
import com.oleksandr.eventprovider.Event.EventMapper;
import com.oleksandr.eventprovider.TicketMaster.dto.TicketmasterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventProviderService {

    private final TicketmasterClient client;
    private final EventMapper mapper;

    @Autowired
    public EventProviderService(TicketmasterClient client, EventMapper mapper) {
        this.client = client;
        this.mapper = mapper;
    }

    public List<Event> getRealEvents() {

        TicketmasterResponse response = client.fetchEvents("PL").block();
        if (response == null || response.getEmbedded() == null) {
            return Collections.emptyList();
        }

        return response.getEmbedded().getEvents().stream()
                .map(mapper::ticketmasterDtoToEvent)
                .collect(Collectors.toList());
    }
}