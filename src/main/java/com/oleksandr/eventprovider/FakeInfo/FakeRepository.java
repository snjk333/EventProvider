package com.oleksandr.eventprovider.FakeInfo;

import com.oleksandr.eventprovider.Event.Event;
import com.oleksandr.eventprovider.Ticket.Ticket;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class FakeRepository {

    public List<Event> events;
    public FakeRepository() {}

    public List<Event> getAllEvents() {
        return events;
    }

    public Event findById(UUID id) {
        return events.stream().filter(event -> event.getId().equals(id)).findFirst().orElse(null);
    }
}
