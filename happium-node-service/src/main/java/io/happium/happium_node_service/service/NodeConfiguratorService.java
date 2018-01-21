package io.happium.happium_node_service.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

/**
 * Experimental class to configure a vanilla Happium node
 * as either a Hub or Node
 */
@Service
public class NodeConfiguratorService {

    /**
     * Utility method to initialize this service instance as
     * a HappiumHub to which HappiumNodes can register to
     */
    public static void initializeAsHub ( JsonNode gridHubConfiguration ) {



    }

    /**
     * Utility method to initialize this service instance as
     * a HappiumNode, which registers to a HappiumHub
     */
    public static void initializeAsNode ( JsonNode gridNodeConfiguration ) {



    }

}
