package com.oleksandr.eventprovider.TicketMaster;

import com.oleksandr.eventprovider.Ticket.Ticket;
import com.oleksandr.eventprovider.Ticket.TicketRepository;
import com.oleksandr.eventprovider.TicketMaster.dto.ReserveTicketSimulation.ReservationRequestDto;
import com.oleksandr.eventprovider.TicketMaster.dto.ReserveTicketSimulation.ReservationResponseDto;
import com.oleksandr.eventprovider.exception.TicketNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Service
public class EventReservationExternalService {

    private final TicketRepository ticketRepository;

    @Value("${reservation.api.baseurl}")
    private String ExternalServiceUrl;

    private final WebClient webClient;

    public EventReservationExternalService(TicketRepository ticketRepository,
                                           WebClient.Builder builder) {
        this.ticketRepository = ticketRepository;
        this.webClient = builder.baseUrl(ExternalServiceUrl).build();
    }

    private String extractEventId(UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow( () -> new TicketNotFoundException("Ticket not found"));
        return ticket.getEvent().getExternalId();
    }

    public ReservationResponseDto createExternalReservation(@Valid ReservationRequestDto requestDto) {
        String externalEventID = this.extractEventId(requestDto.ticketId());

        webClient.post()
                .uri(uriBuilder -> uriBuilder.path("/createReservation")
                        .queryParam("externalEventID", externalEventID)
                        .build())
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(ReservationResponseDto.class)
                .block();

        return new ReservationResponseDto(requestDto.ticketId());
    }

    public ReservationResponseDto cancelExternalReservation(@Valid ReservationRequestDto requestDto) {
        String externalEventID = this.extractEventId(requestDto.ticketId());

        webClient.post()
                .uri(uriBuilder -> uriBuilder.path("/cancelReservation")
                        .queryParam("externalEventID", externalEventID)
                        .build())
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(ReservationResponseDto.class)
                .block();


        return new ReservationResponseDto(requestDto.ticketId());
    }

    public ReservationResponseDto confirmExternalReservation(@Valid ReservationRequestDto requestDto) {
        String externalEventID = this.extractEventId(requestDto.ticketId());

        webClient.post()
                .uri(uriBuilder -> uriBuilder.path("/confirmReservation")
                        .queryParam("externalEventID", externalEventID)
                        .build())
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(ReservationResponseDto.class)
                .block();

        return new ReservationResponseDto(requestDto.ticketId());
    }

}
