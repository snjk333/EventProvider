package com.oleksandr.eventprovider.util;

import com.oleksandr.eventprovider.Event.Event;
import com.oleksandr.eventprovider.Ticket.Ticket;
import com.oleksandr.eventprovider.Ticket.TICKET_STATUS;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class TicketCreationManager {

    public void fillTicketsForAllEvents(List<Event> clearEvents) {
        for (Event event : clearEvents) {
            fillTickets(event);
        }
    }

    public void fillTickets(Event event) {

        if (event.getTickets() == null || event.getTickets().isEmpty()) {

            List<Ticket> tickets = new ArrayList<>();

            //20
            for (int i = 1; i <= 20; i++) {
                String type;
                double price;

                if (i <= 2) { // 2 VIP
                    type = "VIP";
                    price = 200.0;
                } else if (i <= 14) { // 12 Standard
                    type = "STANDARD";
                    price = 100.0;
                } else { // 6 Cheap
                    type = "CHEAP";
                    price = 50.0;
                }

                Ticket ticket = new Ticket(
                        UUID.randomUUID(),
                        event,
                        type,
                        price,
                        TICKET_STATUS.AVAILABLE
                );

                tickets.add(ticket);
            }

            event.setTickets(tickets);
        }
    }
}
