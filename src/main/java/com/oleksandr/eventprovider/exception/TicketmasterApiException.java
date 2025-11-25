package com.oleksandr.eventprovider.exception;

import lombok.Getter;

@Getter
public class TicketmasterApiException extends RuntimeException {

    private final int statusCode;
    private final String responseBody;

    public TicketmasterApiException(String message, int statusCode, String responseBody) {
        super(message);
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

}
