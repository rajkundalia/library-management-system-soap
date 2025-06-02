# Library SOAP System

A complete multi-module Spring Boot project demonstrating SOAP web services architecture with a **Library Service** (SOAP Server) and **Patron Service** (SOAP Client).

## Architecture Overview

```
┌─────────────────┐    REST API    ┌─────────────────┐    SOAP Call    ┌─────────────────┐
│   Web Client    │ ────────────► │ Patron Service  │ ──────────────► │ Library Service │
│                 │               │ (SOAP Client)   │                 │ (SOAP Server)   │
│                 │               │ Port: 8081      │                 │ Port: 8080      │
└─────────────────┘               └─────────────────┘                 └─────────────────┘
```

## Features

### Library Service (SOAP Server)
- **Book Search**: Find books by ISBN
- **Book Reservation**: Reserve available books for patrons
- **WSDL Generation**: Auto-generated SOAP contract
- **Inventory Management**: Track available book counts

### Patron Service (SOAP Client)
- **REST API**: Modern HTTP endpoints for clients
- **SOAP Integration**: Consumes Library Service internally
- **Book Discovery**: Search library catalog
- **Reservation System**: Reserve books for patrons

## Quick Start

### Prerequisites
- Java 17+

### 1. Clone and Build
```bash
git clone <url>
mvn clean install
```

### 2. Start Library Service (SOAP Server)
```bash
cd library-service
mvn spring-boot:run
```
Service available at: http://localhost:8080/ws/library.wsdl

### 3. Start Patron Service (SOAP Client)
```bash
# In new terminal
cd patron-service  
mvn spring-boot:run
``` 
Service available at: http://localhost:8081

## API Testing

### REST API (Patron Service)

#### Search for a Book
```bash
curl http://localhost:8081/patron/search/978-0132350884
```

**Response:**
```json
{
  "isbn": "978-0132350884",
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "available": true,
  "availableCount": 5,
  "message": "Book found in library catalog"
}
```

#### Reserve a Book
```bash
curl -X POST http://localhost:8081/patron/reserve \
  -H "Content-Type: application/json" \
  -d '{"isbn":"978-0132350884","patronId":"patron123"}'
```

**Response:**
```json
{
  "success": true,
  "message": "Book reserved successfully for patron: patron123",
  "patronId": "patron123",
  "isbn": "978-0132350884"
}
```

### SOAP API (Library Service)

#### View WSDL
```bash
curl http://localhost:8080/ws/library.wsdl
```

#### Direct SOAP Call
```bash
curl -X POST http://localhost:8080/ws \
  -H "Content-Type: text/xml; charset=utf-8" \
  -H "SOAPAction: http://example.org/library/getBook" \
  -d '<?xml version="1.0" encoding="UTF-8"?>
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
  <soap:Body>
    <lib:getBookRequest xmlns:lib="http://example.org/library">
      <lib:isbn>978-0132350884</lib:isbn>
    </lib:getBookRequest>
  </soap:Body>
</soap:Envelope>'
```

## Sample Data

The system comes pre-loaded with programming books:

| ISBN | Title | Author | Available |
|------|-------|--------|-----------|
| 978-0132350884 | Clean Code | Robert C. Martin | 5 copies |
| 978-0201633610 | Design Patterns | Gang of Four | 3 copies |
| 978-0134685991 | Effective Java | Joshua Bloch | 0 copies (Out of stock) |

## Project Structure

```
library-soap-system/
├── pom.xml                          # Parent POM
├── README.md
├── library-service/                 # SOAP Server
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/example/library/
│       │   ├── LibraryServiceApplication.java
│       │   ├── config/WebServiceConfig.java
│       │   └── endpoint/
│       │       ├── LibraryEndpoint.java
│       │       └── LibraryService.java
│       └── resources/
│           └── xsd/library.xsd      # SOAP Contract
└── patron-service/                  # SOAP Client
    ├── pom.xml
    └── src/main/
        ├── java/com/example/patron/
        │   ├── PatronServiceApplication.java
        │   ├── config/ClientConfig.java
        │   ├── client/LibraryClient.java
        │   └── controller/PatronController.java
        └── resources/
            ├── application.properties
            └── xsd/library.xsd      # Copy of SOAP Contract
```

## Testing Scenarios

### Scenario 1: Successful Book Search
```bash
# Search for available book
curl http://localhost:8081/patron/search/978-0132350884
# Expected: Book found with availability info
```

### Scenario 2: Book Not Found
```bash
# Search for non-existent book
curl http://localhost:8081/patron/search/invalid-isbn
# Expected: "Book not found in library catalog"
```

### Scenario 3: Successful Reservation
```bash
# Reserve available book
curl -X POST http://localhost:8081/patron/reserve \
  -H "Content-Type: application/json" \
  -d '{"isbn":"978-0132350884","patronId":"patron123"}'
# Expected: Successful reservation, count decreases
```

### Scenario 4: Failed Reservation (Out of Stock)
```bash
# Try to reserve out-of-stock book
curl -X POST http://localhost:8081/patron/reserve \
  -H "Content-Type: application/json" \
  -d '{"isbn":"978-0134685991","patronId":"patron123"}'
# Expected: "Book not available for reservation"
```
## Learning Objectives

This project demonstrates:

1. **SOAP Server Development**: Creating endpoints, WSDL generation, request handling
2. **SOAP Client Integration**: Consuming SOAP services from Spring Boot
3. **Multi-module Architecture**: Managing related services in one project
4. **Contract-First Development**: Using XSD to define service contracts
5. **Legacy Integration Patterns**: Wrapping SOAP with REST APIs

**Star this repository** if you found it helpful for learning SOAP web services!