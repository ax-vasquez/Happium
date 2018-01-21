package io.happium.happium_node_service.service;

import lombok.Getter;
import lombok.Setter;
import org.openqa.grid.selenium.proxy.DefaultRemoteProxy;
import org.springframework.stereotype.Service;

/**
 * EXPERIMENTAL CLASS
 *
 * <p>
 *     This class may be used as the central business logic class
 *     for the happium-node-service. If this is doable, it will
 *     serve as the central node configuration point. This should
 *     allow the happium-node-service to start up without starting
 *     the internal Jetty ServletDispatcher until it's been fully
 *     configured
 */
@Service
public class HappiumNodeService {

    /**
     * Underlying RemoteProxy object used by the corresponding HappiumNode
     * to add/remove test slots and process test requests
     */
    @Getter @Setter private DefaultRemoteProxy defaultRemoteProxy;

    public void setHappiumNodeConfigurationUsingFile ( String filePathToJsonConfigFile ) {



    }

}
