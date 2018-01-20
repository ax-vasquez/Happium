package io.happium.happium_node_service.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.openqa.grid.internal.utils.configuration.GridHubConfiguration;
import org.openqa.grid.web.Hub;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Represents a HappiumHub object
 *
 * <p>
 *     HappiumHub is a wrapper class that contains an instance
 *     of the `selenium-server` Hub class. This is not THE central
 *     element of Happium, but it is one of the most important
 *     elements. Each instance of the `happium-hub-service` contains
 *     an embedded Jetty ServletDispatcher to which selenium server
 *     NODES may register with.
 * <p>
 *     It's important to keep in mind that this service is a SELF
 *     CONTAINED service, meaning that executing the jar built by
 *     the `happium-hub-service` module will only start ONE instance
 *     of the service on the host machine.
 */
@Entity
@NoArgsConstructor
public class HappiumHub implements Serializable {

    /**
     * Underlying Hub object that supports this HappiumHub. This is
     * not persisted to the database
     */
    @Transient
    @Getter @Setter private Hub hub;

    /**
     * Stores this HappiumHub's current configuration
     */
    @Transient
    @Getter @Setter GridHubConfiguration gridHubConfiguration;

    /**
     * Constructor to initialize this HappiumHub using the provided
     * Grid Hub configuration JSON as a string
     *
     * @param gridHubConfigurationJsonString
     */
    public HappiumHub( String gridHubConfigurationJsonString ) {

        hub = new Hub( GridHubConfiguration.loadFromJSON( gridHubConfigurationJsonString ) );

    }

    /**
     * Auto-generate ID for this HappiumHub instance
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "happium_hub_id", nullable = false)
    @Getter @Setter private Long id;

    /**
     * Indicates if this HappiumHub is currently running
     */
    @Column(name = "is_running", columnDefinition = "boolean")
    @Getter @Setter private boolean isRunning;

    /**
     * String representation of the URL where new nodes
     * can register to this hub
     */
    @Column(name = "registration_endpoint", columnDefinition = "text")
    @Getter @Setter private String registrationEndpointUrl;

    /**
     * String representation of the endpoint URL to send new WebDriver
     * requests to for this Hub
     */
    @Column(name = "web_driver_endpoint", columnDefinition = "text")
    @Getter @Setter private String webDriverEndpointUrl;

    /**
     * String representation of the endpoint URL to access this
     * HappiumHub's backing Hub console
     */
    @Column(name = "console_endpoint", columnDefinition = "text")
    @Getter @Setter private String consoleEndpointUrl;

}
