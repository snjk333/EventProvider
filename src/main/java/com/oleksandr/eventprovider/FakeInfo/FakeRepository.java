package com.oleksandr.eventprovider.FakeInfo;

import com.oleksandr.eventprovider.Event.Event;
import com.oleksandr.eventprovider.Ticket.Ticket;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FakeRepository {

    public List<Event> events;

    public FakeRepository() {
        events = new ArrayList<>();

        events.add(new Event(
                UUID.randomUUID(),
                "Rock Fest 2025",
                "Annual open-air rock music festival with top European bands.",
                "Warsaw National Stadium",
                LocalDateTime.of(2025, 7, 12, 18, 0),
                "https://example.com/images/rockfest.jpg",
                new ArrayList<>()
        ));

        events.add(new Event(
                UUID.randomUUID(),
                "Tech Conference 2025",
                "International IT conference covering AI, Cloud and Security.",
                "Kraków Expo Center",
                LocalDateTime.of(2025, 9, 21, 9, 0),
                "https://example.com/images/techconf.jpg",
                new ArrayList<>()
        ));

        events.add(new Event(
                UUID.randomUUID(),
                "Classical Evening",
                "Concert of symphonic orchestra performing Mozart and Beethoven.",
                "Poznań Philharmonic Hall",
                LocalDateTime.of(2025, 10, 3, 19, 30),
                "https://example.com/images/classical.jpg",
                new ArrayList<>()
        ));

        events.add(new Event(
                UUID.randomUUID(),
                "Stand-up Comedy Night",
                "Evening of comedy with popular stand-up artists from Poland.",
                "Wrocław Comedy Club",
                LocalDateTime.of(2025, 11, 15, 20, 0),
                "https://example.com/images/comedy.jpg",
                new ArrayList<>()
        ));

        events.add(new Event(
                UUID.randomUUID(),
                "Christmas Market",
                "Traditional Christmas fair with food, crafts, and entertainment.",
                "Gdańsk Old Town",
                LocalDateTime.of(2025, 12, 10, 16, 0),
                "https://example.com/images/christmas.jpg",
                new ArrayList<>()
        ));
    }

    public List<Event> getAllEvents() {
        return events;
    }

    public Event findById(UUID id) {
        return events.stream().filter(event -> event.getId().equals(id)).findFirst().orElse(null);
    }
}
