package org.example.patron;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PatronServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatronServiceApplication.class, args);
        System.out.println("Patron Service started on http://localhost:8081");
        System.out.println("Try: GET http://localhost:8081/patron/search/978-0132350884");
        System.out.println("Try: POST http://localhost:8081/patron/reserve with {\"isbn\":\"978-0132350884\",\"patronId\":\"patron123\"}");
    }
}