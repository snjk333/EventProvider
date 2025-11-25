package com.oleksandr.eventprovider.Event;

import com.oleksandr.eventprovider.Ticket.TicketDTO;
import com.oleksandr.eventprovider.TicketMaster.EventProviderService;
import com.oleksandr.eventprovider.exception.EventFetchException;
import com.oleksandr.eventprovider.exception.EventNotFoundException;
import com.oleksandr.eventprovider.util.TicketCreationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Primary
public class EventServiceReal implements EventService {

    private static final Logger logger = LoggerFactory.getLogger(EventServiceReal.class);

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final EventProviderService eventProviderService;
    private final TicketCreationManager ticketCreationManager;

    public EventServiceReal(EventRepository eventRepository, 
                           EventMapper eventMapper,
                           EventProviderService eventProviderService,
                           TicketCreationManager ticketCreationManager) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.eventProviderService = eventProviderService;
        this.ticketCreationManager = ticketCreationManager;
    }

    @Override
    @Transactional(readOnly = true)
    public EventDTO getEvent(UUID id, boolean includeTickets) {
        logger.debug("Fetching event with id: {}, includeTickets: {}", id, includeTickets);
        
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found with id: " + id));
        
        if (includeTickets) {
            return eventMapper.mapToDto(event);
        } else {
            return eventMapper.mapToDtoWithoutTickets(event);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketDTO> getTicketsByEvent(UUID id) {
        logger.debug("Fetching tickets for event with id: {}", id);
        
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found with id: " + id));
        
        return eventMapper.mapToDto(event).tickets();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventDTO> getAllEvents(boolean includeTickets) {
        logger.debug("Fetching all events from database, includeTickets: {}", includeTickets);
        
        List<Event> events = eventRepository.findAll();
        
        if (events.isEmpty()) {
            logger.info("No events found in database, fetching from external API");
            return fetchAndSaveEventsFromApi();
        }
        
        return eventMapper.mapListToDtoList(events);
    }

    @Transactional(readOnly = true)
    public Page<EventDTO> getAllEventsPaginated(boolean includeTickets, Pageable pageable) {
        logger.debug("Fetching paginated events from database. Page: {}, Size: {}", 
                     pageable.getPageNumber(), pageable.getPageSize());
        
        Page<Event> eventPage = eventRepository.findAll(pageable);
        
        if (eventPage.isEmpty() && pageable.getPageNumber() == 0) {
            logger.info("No events found in database, fetching from external API");
            fetchAndSaveEventsFromApi();
            eventPage = eventRepository.findAll(pageable);
        }
        
        return eventPage.map(eventMapper::mapToDto);
    }

    @Transactional
    public List<EventDTO> fetchAndSaveEventsFromApi() {
        logger.info("Fetching events from Ticketmaster API");
        
        try {
            List<Event> eventsFromApi = eventProviderService.getRealEvents();
            
            if (eventsFromApi.isEmpty()) {
                logger.warn("No events received from Ticketmaster API");
                return List.of();
            }
            
            logger.info("Received {} events from API", eventsFromApi.size());
            
            // Generate tickets for events
            ticketCreationManager.fillTicketsForAllEvents(eventsFromApi);
            
            // Save events to database
            List<Event> savedEvents = eventRepository.saveAll(eventsFromApi);
            logger.info("Successfully saved {} events to database", savedEvents.size());
            
            return eventMapper.mapListToDtoList(savedEvents);
            
        } catch (Exception e) {
            logger.error("Error fetching and saving events from API: {}", e.getMessage(), e);
            throw new EventFetchException("Failed to fetch events from external API", e);
        }
    }

    @Transactional
    public void refreshEventsFromApi() {
        logger.info("Refreshing events from Ticketmaster API");

        eventRepository.deleteAll();
        logger.info("Deleted all existing events from database");

        fetchAndSaveEventsFromApi();
    }
}
