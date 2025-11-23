package com.oleksandr.eventprovider.TicketMaster.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class StartDateDto {

    private String localDate;
    private String localTime;
    private LocalDateTime dateTime;

}