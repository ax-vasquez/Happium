package io.happium.appium_client_service.appium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Entity representation of AppiumSession
 *
 * <p>
 *     On each new payload receipt (and after the payload has
 *     been validated), a new AppiumSession is created and its
 *     details are stored in a local database.
 * <p>
 *     Extend this class to customize the metrics you wish to
 *     track in your database instance(s).
 * <p>
 *     This class should be seen as the data representation of
 *     a given AppiumSession - it is NOT the only code that
 *     serves to support an Appium Session. The full supporting
 *     code is like so:
 *
 *     (lowest layer) Entity > Repository > Service > Application (highest layer)
 *
 *     Entity is the data layer, Repository is the DAO (data access object),
 *     Service is the "business logic" layer and the Application is the
 *     controlling class (e.g. Application.class). This is
 *     a standard division of labor in a Spring application.
 */
public class AppiumSession {

    /**
     * Desired Capabilities object used to configure the
     * AppiumSession
     */
    @Getter @Setter private DesiredCapabilities desiredCapabilities;

    /**
     * Reference to hosting Appium Server - used to modify the type of OS
     * this session will support via the setDriverAsAndroidDriver() and
     * setDriverAsIosDriver() methods. This is part of what enables this
     * class implementation to be platform-independent
     */
    @Getter @Setter private AppiumServer appiumServer;

    /**
     * Base Appium driver for this session - this will be instantiated
     * as either an iOS or Android-specific driver (the OS-specific
     * implementations extend the functionality of the base driver)
     */
    @Getter @Setter private AppiumDriver driver;

    /**
     * AppiumSession constructor
     *
     * <p>
     *     This takes a single parameter, DesiredCapabilities, to instantiate
     *     a new AppiumSession.
     * <p>
     *     IMPORTANT! The AppiumSession class does not have any validation
     *     code for the Desired Capabilities object - this validation should be
     *     handled in the AppiumSessionService class.
     *
     * @param desiredCapabilities       Validation DesiredCapabilities JSON payload
     */
    public AppiumSession( AppiumServer appiumServer, DesiredCapabilities desiredCapabilities ) {

        this.desiredCapabilities = desiredCapabilities;
        this.appiumServer = appiumServer;

    }

    /**
     * Helper method to configure an Appium Session to use an Android device
     *
     * <p>
     *     The driver MUST be set to either iOS or Android driver in order to
     *     work properly with device interactions
     */
    public void setDriverAsAndroidDriver() {

        driver = new AndroidDriver( appiumServer.getAppiumDriverLocalService(), desiredCapabilities );

    }

    /**
     * Helper method to configure an Appium Session to use an iOS device
     *
     * <p>
     *     The driver MUST be set to either iOS or Android driver in order to
     *     work properly with device interactions
     */
    public void setDriverAsIosDriver() {

        driver = new IOSDriver( appiumServer.getAppiumDriverLocalService(), desiredCapabilities );

    }

    /**
     * Convenient method to print out the session properties
     *
     * @return          Formatted string representing session properties
     */
    @Override
    public String toString() {
        return String.format(
                "AppiumSession[desiredCapabilities='%s']",
                desiredCapabilities
        );
    }

}
