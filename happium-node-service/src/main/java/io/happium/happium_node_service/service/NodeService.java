package io.happium.happium_node_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.happium.happium_node_service.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * Experimental class to configure a vanilla Happium node
 * as either a Hub or Node
 */
@Service
@NoArgsConstructor
public class NodeService {

    @Lazy
    @Autowired
    private HappiumHubCrudRepository happiumHubCrudRepository;

    @Lazy
    @Autowired
    private HappiumNodeCrudRepository happiumNodeCrudRepository;

    private HappiumHub happiumHub;

    private HappiumNode happiumNode;

    /**
     * Utility method to initialize this service instance as
     * a HappiumHub to which HappiumNodes can register to
     */
    public void initializeAsHub ( File configurationJsonFile ) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            // Directly-map file contents to base Node node, using HappiumHub as model
            happiumHub = mapper.readValue( configurationJsonFile, HappiumHub.class );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Utility method to initialize this service instance as
     * a HappiumNode, which registers to a HappiumHub
     */
    public void initializeAsNode ( File configurationJsonFile ) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            // Directly-map file contents to base Node node, using HappiumNode as model
            happiumNode = mapper.readValue( configurationJsonFile, HappiumNode.class );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Service level method to start this service's underlying HappiumHub
     */
    @Async("threadPoolTaskExecutor")
    public void startHappiumHub() {

        try {

            happiumHub.getHub().start();
            happiumHub.setRunning( true );
            happiumHubCrudRepository.save( happiumHub );

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
