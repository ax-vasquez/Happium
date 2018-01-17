package io.happium.selenium_server_service;

import io.happium.selenium_server_service.configuration.DatabaseConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ DatabaseConfiguration.class })
public class Application {

    public static void main ( String [] args ) {

        SpringApplication.run( Application.class, args );

    }

}
