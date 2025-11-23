package com.oleksandr.eventprovider.TicketMaster;

public class TicketmasterApiException extends RuntimeException {

    private final int statusCode;
    private final String responseBody;

    public TicketmasterApiException(String message, int statusCode, String responseBody) {
        super(message);
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseBody() {
        return responseBody;
    }
}
