package org.example.patron.client;

import org.example.library.GetBookRequest;
import org.example.library.GetBookResponse;
import org.example.library.ReserveBookRequest;
import org.example.library.ReserveBookResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class LibraryClient extends WebServiceGatewaySupport {

    public GetBookResponse getBook(String isbn) {
        GetBookRequest request = new GetBookRequest();
        request.setIsbn(isbn);

        GetBookResponse response = (GetBookResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8080/ws", request,
                        new SoapActionCallback("http://example.org/library/getBook"));

        return response;
    }

    public ReserveBookResponse reserveBook(String isbn, String patronId) {
        ReserveBookRequest request = new ReserveBookRequest();
        request.setIsbn(isbn);
        request.setPatronId(patronId);

        ReserveBookResponse response = (ReserveBookResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8080/ws", request,
                        new SoapActionCallback("http://example.org/library/reserveBook"));

        return response;
    }
}