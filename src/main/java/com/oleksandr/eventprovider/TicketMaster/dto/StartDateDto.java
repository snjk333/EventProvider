package com.oleksandr.eventprovider.TicketMaster.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@JsonIgnoreProperties(ignoreUnknown = true)
public record StartDateDto(
         String localDate,
         String localTime,
         LocalDateTime dateTime
) { }