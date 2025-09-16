package com.oleksandr.eventprovider.FakeInfo;


import com.oleksandr.eventprovider.Event.*;
import com.oleksandr.eventprovider.Ticket.TicketDTO;
import com.oleksandr.eventprovider.Ticket.TicketMapper;
import com.oleksandr.eventprovider.Ticket.TicketRepository;
import com.oleksandr.eventprovider.util.TicketCreationManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EventServiceFAKE implements EventService {

    private final FakeRepository fakeRepository;

    private final EventRepository eventRepository;

    private final TicketRepository ticketRepository;

    private final TicketCreationManager ticketCreationManager;

    private final EventMapper eventMapper;

    private final TicketMapper ticketMapper;

    public EventServiceFAKE(FakeRepository fakeRepository, EventRepository eventRepository, TicketRepository ticketRepository, TicketCreationManager ticketCreationManager, EventMapper eventMapper, TicketMapper ticketMapper) {
        this.fakeRepository = fakeRepository;
        this.eventRepository = eventRepository;
        this.ticketRepository = ticketRepository;
        this.ticketCreationManager = ticketCreationManager;
        this.eventMapper = eventMapper;
        this.ticketMapper = ticketMapper;
    }

    @Override
    public EventDTO getEvent(UUID id) {
        Event event = fakeRepository.findById(id);
        return eventMapper.mapToDto(event);
    }

    @Override
    public List<TicketDTO> getTicketsByEvent(UUID id) {
        Event event = fakeRepository.findById(id);
        return ticketMapper.mapEntityListToDtoList(event.getTickets());
    }

    @Override
    public List<EventDTO> getAllEvents(boolean includeTickets) {
       List<Event> ClearEvents = fakeRepository.getAllEvents();
       if(includeTickets) {
           List<Event> EventsWithTickets = ticketCreationManager.fillTickets(ClearEvents);
           return eventMapper.mapListToDtoList(EventsWithTickets);
       }
       return eventMapper.mapListToDtoList(ClearEvents);
    }
}
