package io.happium.android_device_manager_client_service;

import io.happium.android_device_manager_client_service.configuration.AsyncConfiguration;
import io.happium.android_device_manager_client_service.configuration.DatabaseConfiguration;
import io.happium.android_device_manager_client_service.configuration.EurekaClientConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({AsyncConfiguration.class, DatabaseConfiguration.class, EurekaClientConfiguration.class})
public class Application {

    public static void main(String[] args) {

        SpringApplication.run( Application.class, args );

    }

}
