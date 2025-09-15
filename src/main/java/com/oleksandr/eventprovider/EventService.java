package com.oleksandr.eventprovider;


import java.util.List;
import java.util.UUID;

public interface EventService {

    EventDTO getEvent(UUID id);

    List<TicketDTO> getTicketsByEvent(UUID id, String status, Double minPrice, Double maxPrice, int ticketsPage, int ticketsSize);

    List<EventDTO> getAllEvents(int page, int size, boolean includeTickets);

    EventDTO getEventById(UUID id, boolean includeTickets, int ticketsPage, int ticketsSize);
}
