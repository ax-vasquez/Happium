package io.happium.happium_hub_service.persistence;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class HappiumHub {

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
    @Column(name = "is_running", nullable = false)
    @Getter @Setter private boolean isRunning;

    /**
     * String representation of the URL where new nodes
     * can register to this hub
     */
    @Column(name = "registration_endpoint", nullable = false)
    @Getter @Setter private String registrationEndpointUrl;

    /**
     * String representation of the endpoint URL to send new WebDriver
     * requests to for this Hub
     */
    @Column(name = "web_driver_endpoint", nullable = false)
    @Getter @Setter private String webDriverEndpointUrl;

    /**
     * String representation of the endpoint URL to access this
     * HappiumHub's backing Hub console
     */
    @Column(name = "console_endpoint", nullable = false)
    @Getter @Setter private String consoleEndpointUrl;

    /**
     * Number of proxies currently registered to this hub
     */
    @Column(name = "reg_proxy_count", columnDefinition = "int")
    @Getter @Setter private int registeredProxyCount;

}
