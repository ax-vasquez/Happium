package io.happium.appium_client_service;

import io.happium.appium_client_service.configuration.DatabaseConfiguration;
import io.happium.appium_client_service.configuration.EurekaClientConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({EurekaClientConfiguration.class, DatabaseConfiguration.class})
public class Application {

    public static void main(String[] args) {

        SpringApplication.run( Application.class, args );

    }

}
