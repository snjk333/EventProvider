package com.oleksandr.eventprovider.Event;

import com.oleksandr.eventprovider.Ticket.Ticket;
import com.oleksandr.eventprovider.Ticket.TicketMapper;
import com.oleksandr.eventprovider.TicketMaster.dto.EventMasterDto;
import com.oleksandr.eventprovider.TicketMaster.dto.ImageDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class EventMapper {

    private final TicketMapper ticketMapper;

    public EventMapper(TicketMapper ticketMapper) {
        this.ticketMapper = ticketMapper;
    }

    public Event mapToEntity(EventDTO dto) {
        if (dto == null) throw new IllegalArgumentException("EventDTO cannot be null");

        Event event = new Event();
        event.setId(dto.getId());
        event.setName(dto.getName());
        event.setDescription(dto.getDescription());
        event.setLocation(dto.getLocation());
        event.setImageURL(dto.getImageURL());
        event.setEventDate(dto.getEventDate());

        if (dto.getTickets() != null) {
            List<Ticket> tickets = ticketMapper.mapTicketsListFromDto(dto.getTickets());
            tickets.forEach(t -> t.setEvent(event));
            event.setTickets(tickets);
        } else {
            event.setTickets(new ArrayList<>());
        }

        return event;
    }
    
    public Event updateEventInformation(Event eventToChange, EventDTO dto) {
        if (dto == null) return eventToChange;

        if (dto.getName() != null) eventToChange.setName(dto.getName());
        if (dto.getDescription() != null) eventToChange.setDescription(dto.getDescription());
        if (dto.getLocation() != null) eventToChange.setLocation(dto.getLocation());
        if (dto.getImageURL() != null) eventToChange.setImageURL(dto.getImageURL());
        if (dto.getEventDate() != null) eventToChange.setEventDate(dto.getEventDate());

        if (dto.getTickets() != null) {
            List<Ticket> updatedTickets = ticketMapper.mapTicketsListFromDto(dto.getTickets());
            for (Ticket updated : updatedTickets) {
                if (updated.getId() == null) {
                    updated.setEvent(eventToChange);
                    eventToChange.getTickets().add(updated);
                } else {
                    boolean found = false;
                    for (int i = 0; i < eventToChange.getTickets().size(); i++) {
                        Ticket current = eventToChange.getTickets().get(i);
                        if (current.getId().equals(updated.getId())) {
                            updated.setEvent(eventToChange);
                            eventToChange.getTickets().set(i, updated);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        updated.setEvent(eventToChange);
                        eventToChange.getTickets().add(updated);
                    }
                }
            }
        }

        return eventToChange;
    }

    // Entity → DTO
    public EventDTO mapToDto(Event event) {
        if (event == null) throw new IllegalArgumentException("Event entity cannot be null");

        return EventDTO.builder()
                .id(event.getId())
                .name(event.getName())
                .description(event.getDescription())
                .location(event.getLocation())
                .imageURL(event.getImageURL())
                .eventDate(event.getEventDate())
                .tickets(event.getTickets() != null
                        ? ticketMapper.mapEntityListToDtoList(event.getTickets())
                        : List.of())
                .build();
    }

    public List<EventDTO> mapListToDtoList(List<Event> events) {
        return events == null ? List.of() :
                events.stream()
                        .map(this::mapToDto)
                        .filter(Objects::nonNull)
                        .toList();
    }

    public EventDTO mapToDtoWithoutTickets(Event event) {
        if (event == null) throw new IllegalArgumentException("Event entity cannot be null");

        return EventDTO.builder()
                .id(event.getId())
                .name(event.getName())
                .description(event.getDescription())
                .location(event.getLocation())
                .imageURL(event.getImageURL())
                .eventDate(event.getEventDate())
                .tickets(new ArrayList<>())
                .build();
    }

    public EventDTO ticketmasterToInternalDto(EventMasterDto ticketmasterDto) {
        if (ticketmasterDto == null) {
            return null;
        }

        EventDTO internalDto = new EventDTO();

        internalDto.setId(null);

        internalDto.setName(ticketmasterDto.getName());

        internalDto.setEventDate(parseEventDate(ticketmasterDto.getDates()));

        internalDto.setImageURL(extractImageUrl(ticketmasterDto.getImages()));

        // TODO: Location extraction requires venue data structure
        internalDto.setLocation("TBD");
        internalDto.setDescription(ticketmasterDto.getName() != null ? ticketmasterDto.getName() : "No description available.");


        internalDto.setTickets(new ArrayList<>());

        return internalDto;
    }

    public Event ticketmasterDtoToEvent(EventMasterDto ticketmasterDto) {
        if (ticketmasterDto == null) {
            return null;
        }

        EventDTO eventDto = ticketmasterToInternalDto(ticketmasterDto);

        Event event = mapToEntity(eventDto);

        event.setExternalId(ticketmasterDto.getId());
        
        return event;
    }


    private String extractImageUrl(List<ImageDto> images) {
        if (images == null || images.isEmpty()) {
            return null;
        }


        Optional<ImageDto> preferredImage = images.stream()
                .filter(img -> "16_9".equals(img.getRatio()) || "3_2".equals(img.getRatio()))
                .max(Comparator.comparingInt(ImageDto::getWidth));

        return preferredImage.map(ImageDto::getUrl).orElse(images.get(0).getUrl());
    }


    private LocalDateTime parseEventDate(com.oleksandr.eventprovider.TicketMaster.dto.DatesDto datesDto) {
        if (datesDto == null || datesDto.getStart() == null) {
            return null;
        }

        var start = datesDto.getStart();

        if (start.getDateTime() != null) {
            return start.getDateTime();
        }

        if (start.getLocalDate() != null) {
            java.time.LocalDate date = java.time.LocalDate.parse(start.getLocalDate());
            
            if (start.getLocalTime() != null) {
                java.time.LocalTime time = java.time.LocalTime.parse(start.getLocalTime());
                return LocalDateTime.of(date, time);
            } else {
                return date.atStartOfDay();
            }
        }

        return null;
    }

    // TODO: Implement extractLocation when venue DTOs are available
    // Location extraction requires VenueDto and proper EventEmbedded structure
}

