package com.oleksandr.eventprovider.rest;

import com.oleksandr.eventprovider.Event.EventDTO;
import com.oleksandr.eventprovider.Event.EventServiceReal;
import com.oleksandr.eventprovider.Ticket.TicketDTO;
import com.oleksandr.eventprovider.Event.EventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/external")
public class MainController {

    private final EventService eventService;

    public MainController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/events")
    public Map<String, Object> getAllEvents(
            @RequestParam(value = "includeTickets", defaultValue = "false") boolean includeTickets,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EventDTO> eventPage = eventService.getAllEventsPaginated(includeTickets, pageable);
        
        Map<String, Object> response = new HashMap<>();
        response.put("events", eventPage.getContent());
        response.put("currentPage", eventPage.getNumber());
        response.put("totalItems", eventPage.getTotalElements());
        response.put("totalPages", eventPage.getTotalPages());
        
        return response;
    }

    @PostMapping("/events/refresh")
    public ResponseEntity<Map<String, String>> refreshEventsFromApi() {
        eventService.refreshEventsFromApi();
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Events successfully refreshed from Ticketmaster API");
        response.put("status", "success");
        
        return ResponseEntity.ok(response);
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
