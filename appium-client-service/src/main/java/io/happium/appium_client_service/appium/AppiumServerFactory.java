package io.happium.appium_client_service.appium;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class AppiumServerFactory {

    private static final AppiumServiceBuilder serverBuilder = new AppiumServiceBuilder();

    /**
     * Starts a new server at the provided IP address, listening on any free
     * port. Log events are written to the provided file. This does not start
     * the server, but merely stages it for starting.
     *
     * @param ipAddress         IP Address to listen on
     */
    public AppiumDriverLocalService buildNewServer( String ipAddress ) {

        return AppiumDriverLocalService.buildService(
                serverBuilder
                        .withIPAddress( ipAddress )
                        .usingAnyFreePort()
                        .withArgument( GeneralServerFlag.LOG_LEVEL, "debug")
        );

    }

}