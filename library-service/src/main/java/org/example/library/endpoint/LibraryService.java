package org.example.library.endpoint;

import jakarta.annotation.PostConstruct;
import org.example.library.BookType;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LibraryService {
    
    private Map<String, BookType> bookInventory;

    @PostConstruct
    public void initData() {
        bookInventory = new HashMap<>();
        
        // Initialize with sample books
        BookType book1 = new BookType();
        book1.setIsbn("978-0132350884");
        book1.setTitle("Clean Code");
        book1.setAuthor("Robert C. Martin");
        book1.setAvailableCount(5);
        bookInventory.put(book1.getIsbn(), book1);
        
        BookType book2 = new BookType();
        book2.setIsbn("978-0201633610");
        book2.setTitle("Design Patterns");
        book2.setAuthor("Gang of Four");
        book2.setAvailableCount(3);
        bookInventory.put(book2.getIsbn(), book2);
        
        BookType book3 = new BookType();
        book3.setIsbn("978-0134685991");
        book3.setTitle("Effective Java");
        book3.setAuthor("Joshua Bloch");
        book3.setAvailableCount(0); // Out of stock
        bookInventory.put(book3.getIsbn(), book3);
    }

    public BookType findBookByIsbn(String isbn) {
        return bookInventory.get(isbn);
    }

    public boolean reserveBook(String isbn, String patronId) {
        BookType book = bookInventory.get(isbn);
        
        if (book != null && book.getAvailableCount() > 0) {
            book.setAvailableCount(book.getAvailableCount() - 1);
            System.out.println("Book reserved: " + book.getTitle() + " for patron: " + patronId);
            return true;
        }
        
        return false;
    }
}