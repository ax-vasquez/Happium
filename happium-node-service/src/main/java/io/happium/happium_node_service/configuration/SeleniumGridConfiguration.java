package io.happium.happium_node_service.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.grid.internal.utils.configuration.GridHubConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.*;

@Configuration
public class SeleniumGridConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * This Bean sets the default configuration for the base HappiumHub.
     *
     * @return
     */
    @Bean
    public GridHubConfiguration defaultGridHubConfiguration() {

        Resource defaultJsonResource = applicationContext.getResource( "classpath:defaults/DefaultHub.json" );
        ObjectMapper mapper = new ObjectMapper();

        try {

            return GridHubConfiguration.loadFromJSON( defaultJsonResource.getFile().getPath() );

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

}
