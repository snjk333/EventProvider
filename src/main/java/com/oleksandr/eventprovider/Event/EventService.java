package com.oleksandr.eventprovider.Event;


import com.oleksandr.eventprovider.Ticket.TicketDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface EventService {

    EventDTO getEvent(UUID id, boolean includeTickets);

    List<TicketDTO> getTicketsByEvent(UUID id);

    List<EventDTO> getAllEvents(boolean includeTickets);

    Page<EventDTO> getAllEventsPaginated(boolean includeTickets, Pageable pageable);

    List<EventDTO> fetchAndSaveEventsFromApi();

    void refreshEventsFromApi();

    //EventDTO getEventById(UUID id, boolean includeTickets);
}
