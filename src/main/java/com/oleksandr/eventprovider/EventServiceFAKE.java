package com.oleksandr.eventprovider;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EventServiceFAKE implements EventService {

    @Override
    public EventDTO getEvent(UUID id) {
        return null;
    }

    @Override
    public List<TicketDTO> getTicketsByEvent(UUID id, String status, Double minPrice, Double maxPrice, int ticketsPage, int ticketsSize) {
        return List.of();
    }

    @Override
    public List<EventDTO> getAllEvents(int page, int size, boolean includeTickets) {
        return List.of();
    }

    @Override
    public EventDTO getEventById(UUID id, boolean includeTickets, int ticketsPage, int ticketsSize) {
        return null;
    }
}
