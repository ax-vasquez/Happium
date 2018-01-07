package io.happium.appium_client_service.persistence;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

/**
 * Appium Server Entity representation class
 *
 * <p>
 *     Stores information about an Appium server instance
 *     and stores that instance as an entity in the target
 *     database (database configured in DatabaseConfiguration class
 *     from the io.happium.appium_client_service.configuration.* package)
 */
@Entity
public class AppiumServerEntity {

    /**
     * Auto-generated server ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter Long id;

    /**
     * Easy-to-remember reference to this server
     */
    @Getter @Setter String nickname;

    /**
     * Fully-qualified URL of server as a string
     */
    @Getter @Setter String url;

    /**
     * Represents Appium Server
     */
    @Transient
    @Getter @Setter private AppiumDriverLocalService appiumDriverLocalService;  // Not persisted to table

    /**
     * Constructor is initialized with a new AppiumDriverLocalService
     *
     * <p>
     *     IMPORTANT: The appiumDriverLocalService that's passed to this
     *     constructor is only INITIALIZED, but not started. Starting
     *     the server should be done in the service class.
     *
     * @param appiumDriverLocalService          initialized server instance
     */
    @Autowired
    public AppiumServerEntity( AppiumDriverLocalService appiumDriverLocalService, String nickname ) {

        this.url = appiumDriverLocalService.getUrl().toString();
        this.nickname = nickname;

    }

    /**
     * Helper method to start this Appium server
     */
    public void startServer() {

        this.appiumDriverLocalService.start();

    }

    /**
     * Helper method to stop this Appium server
     */
    public void stopServer() {

        this.appiumDriverLocalService.stop();

    }

    /**
     * Helper method to check whether this server instance is running
     *
     * @return          true if the server is running, otherwise false
     */
    public boolean serverIsRunning() {

        return this.appiumDriverLocalService.isRunning();

    }

    /**
     * Helper method to retrieve the log output if the server
     * has been run
     *
     * @return          Logged output if server has been run, otherwise null
     */
    public String getServerLogOutput() {

        return this.appiumDriverLocalService.getStdOut();

    }

    @Override
    public String toString() {
        return String.format(
                "AppiumServer[url='%s']",
                url
        );
    }

}
