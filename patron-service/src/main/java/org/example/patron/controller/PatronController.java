package org.example.patron.controller;

import org.example.library.BookType;
import org.example.library.GetBookResponse;
import org.example.library.ReserveBookResponse;
import org.example.patron.client.LibraryClient;
import org.example.patron.dto.PatronBookInfo;
import org.example.patron.dto.ReservationRequest;
import org.example.patron.dto.ReservationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patron")
public class PatronController {

    @Autowired
    private LibraryClient libraryClient;

    @GetMapping("/search/{isbn}")
    public PatronBookInfo searchBook(@PathVariable(name = "isbn") String isbn) {
        GetBookResponse response = libraryClient.getBook(isbn);
        
        PatronBookInfo bookInfo = new PatronBookInfo();
        if (response.isFound()) {
            BookType book = response.getBook();
            bookInfo.setIsbn(book.getIsbn());
            bookInfo.setTitle(book.getTitle());
            bookInfo.setAuthor(book.getAuthor());
            bookInfo.setAvailable(book.getAvailableCount() > 0);
            bookInfo.setAvailableCount(book.getAvailableCount());
            bookInfo.setMessage("Book found in library catalog");
        } else {
            bookInfo.setMessage("Book not found in library catalog");
        }
        
        return bookInfo;
    }

    @PostMapping("/reserve")
    public ReservationResult reserveBook(@RequestBody ReservationRequest request) {
        ReserveBookResponse response = libraryClient.reserveBook(
            request.getIsbn(), 
            request.getPatronId()
        );
        
        ReservationResult result = new ReservationResult();
        result.setSuccess(response.isSuccess());
        result.setMessage(response.getMessage());
        result.setPatronId(request.getPatronId());
        result.setIsbn(request.getIsbn());
        
        return result;
    }
}