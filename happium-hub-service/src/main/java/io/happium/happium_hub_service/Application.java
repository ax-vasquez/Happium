package io.happium.happium_hub_service;

import io.happium.happium_hub_service.configuration.AsyncConfiguration;
import io.happium.happium_hub_service.configuration.DatabaseConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({AsyncConfiguration.class, DatabaseConfiguration.class})
public class Application {

    public static void main ( String [] args ) {

        SpringApplication.run( Application.class, args );

    }

}
