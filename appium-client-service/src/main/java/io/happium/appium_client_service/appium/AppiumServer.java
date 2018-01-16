package io.happium.appium_client_service.appium;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import lombok.Getter;
import lombok.Setter;

/**
 * Appium Server Class
 *
 * <p>
 *     Servers are created as soon as Happium is aware
 *     that a new server is needed. A new server is needed
 *     when the ALL of the following criteria have been met
 *     (NOTE: None of these criteria are handled by the
 *     AppiumServer class - these are just the milestones
 *     that have to happen before a new server can be created):
 *
 *     1). A new payload has been received
 *     2). That payload has been validated
 *     3). The target device's "inUse" status is FALSE
 *     4). Happium can interpret any custom test instruction code
 *
 * <p>
 *     After the above four requirements are met, Happium
 *     will then instantiate a new server at the indicated
 *     URL (on any free port to avoid port conflicts - it's
 *     best not to change this as you'll need to implement
 *     custom code to avoid such conflicts). The server is spun
 *     up in an asynchronous thread that is kept alive until
 *     the Appium Session has completed. Once all results have
 *     been generated and returned, the server instance is
 *     torn down.
 */
public class AppiumServer {

    /**
     * Fully-qualified URL of server as a string
     */
    @Getter @Setter String url;

    /**
     * Represents Appium Server
     */
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
    public AppiumServer(AppiumDriverLocalService appiumDriverLocalService ) {

        this.url = appiumDriverLocalService.getUrl().toString();

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
