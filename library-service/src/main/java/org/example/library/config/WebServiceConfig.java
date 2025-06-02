package org.example.library.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

// Foundation for implementing @Endpoint annotated service classes
@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    /*
    Handles incoming SOAP requests

    /ws/* endpoint becomes the base URL for SOAP operations

    transformWsdlLocations=true ensures WSDL's <soap:address> reflects actual server URL
     */
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    /*
    Auto-generates WSDL at http://localhost:8080/ws/library.wsdl

    library bean name determines WSDL filename (library.wsdl)

    Uses contract-first approach based on XSD schema
     */
    @Bean(name = "library")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema librarySchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("LibraryPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://example.org/library");
        wsdl11Definition.setSchema(librarySchema);
        return wsdl11Definition;
    }

    /*
    library.xsd defines data contracts for SOAP messages

    Spring automatically detects XSD in resources/xsd

    Schema used for both request validation and WSDL generation
     */
    @Bean
    public XsdSchema librarySchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/library.xsd"));
    }
}