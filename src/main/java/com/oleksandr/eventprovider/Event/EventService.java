package com.oleksandr.eventprovider.Event;


import com.oleksandr.eventprovider.Ticket.TicketDTO;

import java.util.List;
import java.util.UUID;

public interface EventService {

    EventDTO getEvent(UUID id);

    List<TicketDTO> getTicketsByEvent(UUID id);

    List<EventDTO> getAllEvents(boolean includeTickets);

    //EventDTO getEventById(UUID id, boolean includeTickets);
}
