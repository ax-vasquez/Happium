package io.happium.happium_core.persistence;

import io.happium.happium_core.util.Browser;
import io.happium.happium_core.util.Platform;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.service.DriverService;

import javax.persistence.*;

@Entity
public class Payload {

    /**
     * Happium-specific Payload Identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payload_id", nullable = false)
    @Getter @Setter private Long payloadId;

    /**
     * Populated via getSessionId() method
     */
    @Column(name = "selenium_session_id", nullable = false, columnDefinition = "text")
    @Getter @Setter private String seleniumSessionId;

    /**
     * Stores the platform name for the host system (if testing browser), or
     * mobile operating system
     */
    @Column(name = "platform_name", nullable = false, columnDefinition = "text")
    @Getter @Setter private Platform platform;

    /**
     * Optional attribute that sets the type of browser this is - not applicable
     * when testing native mobile apps
     */
    @Column(name = "browser_name", columnDefinition = "text")
    @Getter @Setter private Browser browser;

    /**
     * URL to return to between test suites
     */
    @Column(name = "default_url", columnDefinition = "text")
    @Getter @Setter private String defaultUrl;

    /**
     * Base object to store the driver for the given session - this is not persisted
     * to the database. In Appium sessions, this is instantiated as an AppiumDriverLocalService.
     * This object "hosts" a RemoteWebDriver instance in operation, but does not
     * control the interactions with the browser/device
     */
    @Transient
    @Getter @Setter private DriverService driverService;

    /**
     * Base object to store the interactions driver for the given session - this is not
     * persisted to the databse. In Appium sessions, this is instantiated as an AppiumDriver
     * (platform-specific for iOS or Android). This cannot function without an underlying
     * (running) DriverService
     */
    @Transient
    @Getter @Setter private RemoteWebDriver remoteWebDriver;

}
