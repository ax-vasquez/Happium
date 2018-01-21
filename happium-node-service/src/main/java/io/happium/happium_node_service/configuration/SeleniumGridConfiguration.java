package io.happium.happium_node_service.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.grid.internal.utils.configuration.GridHubConfiguration;
import org.openqa.grid.internal.utils.configuration.GridNodeConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;

import java.io.*;

@Configuration
public class SeleniumGridConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Loads the settings defined under /src/main/java/resources/defaults/DefaultHub.json
     *
     * @return
     */
    @Bean
    @Lazy
    public GridHubConfiguration loadDefaultHubConfiguration() {

        Resource defaultJsonResource = applicationContext.getResource( "classpath*:defaults/DefaultHub.json" );
        ObjectMapper mapper = new ObjectMapper();

        try {

            return GridHubConfiguration.loadFromJSON( defaultJsonResource.getFile().getPath() );

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * Loads the settings defined in NodeConfiguration.json
     *
     * @return
     */
    @Bean
    @Lazy
    public GridNodeConfiguration loadHappiumNodeConfiguration() {

        Resource configFileResource = applicationContext.getResource("NodeConfiguration.json");

        ObjectMapper mapper = new ObjectMapper();

        try {

            return GridNodeConfiguration.loadFromJSON( configFileResource.getFile().getPath() );

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * Loads the settings defined in NodeConfiguration.json
     *
     * @return
     */
    @Bean
    @Lazy
    public GridHubConfiguration loadHappiumHubConfiguration() {

        Resource configFileResource = applicationContext.getResource("NodeConfiguration.json");

        ObjectMapper mapper = new ObjectMapper();

        try {

            return GridHubConfiguration.loadFromJSON( configFileResource.getFile().getPath() );

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

}
