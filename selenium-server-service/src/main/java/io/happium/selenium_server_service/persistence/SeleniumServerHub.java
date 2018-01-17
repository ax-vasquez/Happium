package io.happium.selenium_server_service.persistence;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.openqa.selenium.remote.server.SeleniumServer;

import javax.annotation.PostConstruct;
import javax.persistence.*;

/**
 * Model Entity class for a Selenium Server hub.
 *
 * <p>
 *     A SeleniumServerHub is a core registry service using the Jetty ServletDispatcher.
 *     This servlet dispatcher is separate from the core Tomcat servlet dispatcher utilized
 *     within the Eureka registry service package. In operation, Each SeleniumServerHub will
 *     subscribe to the core Eureka registry service. Each SeleniumServerHub each has its
 *     own set of nodes subscribed to it. These nodes represent the testing environment (e.g.
 *     Firefox browser instance).
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SeleniumServerHub {

    /**
     * Unique ID (auto-generated)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "selenium_hub_id", nullable = false)
    @Getter @Setter private long id;

    /**
     * Port that the given hub is running on
     */
    @Column(name = "real_port", columnDefinition = "int")
    @Getter @Setter private int realPort;

    /**
     * Number of nodes registered to this server hub
     */
    @Column(name = "registered_nodes", columnDefinition = "int")
    @Getter @Setter private int registeredNodes;

    /**
     * This instance's base SeleniumServer
     */
    @Transient
    @Getter @Setter private SeleniumServer seleniumServer;

    /**
     * Constructor takes SeleniumServer parameter to initialize all other
     * member variables
     *
     * @param seleniumServer            Base Selenium Server that this class represents
     */
    public SeleniumServerHub( SeleniumServer seleniumServer ) {

        this.seleniumServer = seleniumServer;

    }

    /**
     * Initializes attributes after construction of a new SeleniumServerHub
     */
    @PostConstruct
    private void _init() {

        realPort = seleniumServer.getRealPort();
        registeredNodes = 0;

    }

}
