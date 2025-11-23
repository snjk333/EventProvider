package com.oleksandr.eventprovider.TicketMaster.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DatesDto {

    private StartDateDto start;

}