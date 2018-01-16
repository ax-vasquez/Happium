package io.happium.junit_client_service.supported_capabilities_client_service;

import io.happium.junit_client_service.supported_capabilities_client_service.configuration.DatabaseConfiguration;
import io.happium.supported_capabilities_client_service.configuration.DatabaseConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ DatabaseConfiguration.class })
public class Application {

    public static void main(String[] args) {

        SpringApplication.run( Application.class, args );

    }

}
