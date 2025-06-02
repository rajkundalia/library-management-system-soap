package org.example.library.endpoint;


import org.example.library.BookType;
import org.example.library.GetBookRequest;
import org.example.library.GetBookResponse;
import org.example.library.ReserveBookRequest;
import org.example.library.ReserveBookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class LibraryEndpoint {

    private static final String NAMESPACE_URI = "http://example.org/library";

    @Autowired
    private LibraryService libraryService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getBookRequest")
    @ResponsePayload
    public GetBookResponse getBook(@RequestPayload GetBookRequest request) {
        GetBookResponse response = new GetBookResponse();

        BookType book = libraryService.findBookByIsbn(request.getIsbn());

        if (book != null) {
            response.setBook(book);
            response.setFound(true);
        } else {
            response.setFound(false);
        }

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "reserveBookRequest")
    @ResponsePayload
    public ReserveBookResponse reserveBook(@RequestPayload ReserveBookRequest request) {
        ReserveBookResponse response = new ReserveBookResponse();

        boolean success = libraryService.reserveBook(request.getIsbn(), request.getPatronId());

        response.setSuccess(success);
        if (success) {
            response.setMessage("Book reserved successfully for patron: " + request.getPatronId());
        } else {
            response.setMessage("Book not available for reservation");
        }

        return response;
    }
}