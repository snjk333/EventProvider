package com.oleksandr.eventprovider;

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
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "-1") int size,
            @RequestParam(value = "includeTickets", defaultValue = "false") boolean includeTickets
    ) {
        return eventService.getAllEvents(page, size, includeTickets);
    }

    @GetMapping("/events/{id}")
    public EventDTO getEventByUUID(
            @PathVariable("id") UUID id,
            @RequestParam(value = "includeTickets", defaultValue = "false") boolean includeTickets,
            @RequestParam(value = "ticketsPage", defaultValue = "0") int ticketsPage,
            @RequestParam(value = "ticketsSize", defaultValue = "20") int ticketsSize
    ) {
        return eventService.getEventById(id, includeTickets, ticketsPage, ticketsSize);
    }

    @GetMapping("/events/{id}/tickets")
    public List<TicketDTO> getTicketsByEvent(
            @PathVariable("id") UUID id,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @RequestParam(value = "ticketsPage", defaultValue = "0") int ticketsPage,
            @RequestParam(value = "ticketsSize", defaultValue = "20") int ticketsSize
    ) {
        return eventService.getTicketsByEvent(id, status, minPrice, maxPrice, ticketsPage, ticketsSize);
    }
}
