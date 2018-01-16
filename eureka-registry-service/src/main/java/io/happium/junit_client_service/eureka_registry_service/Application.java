package io.happium.junit_client_service.eureka_registry_service;

import io.happium.eureka_registry_service.configuration.EurekaServerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.Properties;

@SpringBootApplication
@Import({EurekaServerConfiguration.class})
public class Application {

    public static void main(String [] args) {

        SpringApplication.run(Application.class, args);

    }

    @Bean
    public Properties properties() {

        Properties properties = new Properties();
        properties.setProperty("logging.level.com.netflix.eureka", "OFF");
        properties.setProperty("logging.level.com.netflix.discovery", "OFF");
        return properties;

    }

}
