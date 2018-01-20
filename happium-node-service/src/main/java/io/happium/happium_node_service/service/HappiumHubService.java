package io.happium.happium_node_service.service;

import io.happium.happium_node_service.persistence.HappiumHub;
import io.happium.happium_node_service.persistence.HappiumHubCrudRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.openqa.grid.internal.utils.configuration.GridHubConfiguration;
import org.openqa.grid.web.Hub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Service class for HappiumHub
 *
 * <p>
 *     This class controls all operational logic for
 *     the HappiumHub object.
 */
@Service
@NoArgsConstructor
public class HappiumHubService {

    /**
     * Required to allow this service access to the HappiumHub data table
     */
    @Autowired
    @Getter @Setter private HappiumHubCrudRepository happiumHubCrudRepository;

    /**
     * Loads the provided default gridHubConfiguration located in resources/defaults/
     */
    @Autowired
    @Getter @Setter private GridHubConfiguration defaultGridHubConfiguration;

    /**
     * This service instance's supporting HappiumHub that all operations are
     * performed on
     */
    @Getter @Setter private HappiumHub happiumHub;

    /**
     * Utility method to initialize this service's HappiumHub using
     * the default settings
     */
    public void setHappiumHubUsingDefaults() {

        happiumHub = new HappiumHub();
        happiumHub.setHub( new Hub( defaultGridHubConfiguration ));
        happiumHub.setRunning( false );

    }

    /**
     * Configures this hub using the configuration provided in the String
     * representation of a GridHubConfiguration object
     *
     * @param gridHubConfigJsonString
     */
    public void setHappiumHubUsingJsonConfig( String gridHubConfigJsonString ) {

        happiumHub = new HappiumHub( gridHubConfigJsonString );
        happiumHubCrudRepository.save( happiumHub );

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

    /**
     * Service level method to stop this service's underlying HappiumHub
     */
    public void stopHappiumHub() {

        try {
            happiumHub.getHub().stop();
            happiumHub.setRunning( false );
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Utility method to retrieve the underlying HappiumHub's
     * Configuration
     *
     * @return
     */
    public String getHappiumHubConfiguration() {

        return happiumHub.getHub().getConfiguration().toString();

    }

    /**
     * Convenience method to get the count of all pending session requests
     *
     * @return
     */
    public int getPendingSessionRequestCount() {

        return happiumHub.getHub().getNewSessionRequestCount();

    }

}
