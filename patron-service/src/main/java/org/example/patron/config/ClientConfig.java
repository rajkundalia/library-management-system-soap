package org.example.patron.config;

import org.example.patron.client.LibraryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class ClientConfig {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // This package contains the generated classes from XSD
        marshaller.setContextPath("org.example.library");
        return marshaller;
    }

    @Bean
    public LibraryClient libraryClient(Jaxb2Marshaller marshaller) {
        LibraryClient client = new LibraryClient();
        client.setDefaultUri("http://localhost:8080/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}