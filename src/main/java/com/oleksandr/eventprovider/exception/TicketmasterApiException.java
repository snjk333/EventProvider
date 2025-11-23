package com.oleksandr.eventprovider.exception;

public class TicketmasterApiException extends RuntimeException {
    public TicketmasterApiException(String message) {
        super(message);
    }

    public TicketmasterApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
