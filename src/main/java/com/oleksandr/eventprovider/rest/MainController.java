package com.oleksandr.eventprovider.rest;

import com.oleksandr.eventprovider.Event.EventDTO;
import com.oleksandr.eventprovider.Ticket.TicketDTO;
import com.oleksandr.eventprovider.Event.EventService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/external")
public class MainController {

    private final EventService eventService;

    public MainController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/events")
    public List<EventDTO> getAllEvents(
            @RequestParam(value = "includeTickets", defaultValue = "false") boolean includeTickets
    ) {
        return eventService.getAllEvents(includeTickets);
    }

    @GetMapping("/events/{id}")
    public EventDTO getEventByUUID(
            @PathVariable("id") UUID id,
            @RequestParam(value = "includeTickets", defaultValue = "false") boolean includeTickets
    ) {
        return eventService.getEvent(id, includeTickets);
    }

    @GetMapping("/events/{id}/tickets")
    public List<TicketDTO> getTicketsByEvent(
            @PathVariable("id") UUID id
    ) {
        return eventService.getTicketsByEvent(id);
    }
}
