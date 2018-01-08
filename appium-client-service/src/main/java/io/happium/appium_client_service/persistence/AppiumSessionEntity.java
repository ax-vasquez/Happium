package io.happium.appium_client_service.persistence;

import io.appium.java_client.AppiumDriver;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.remote.DesiredCapabilities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Hashtable;

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
@Entity
public class AppiumSessionEntity implements Serializable {

    /**
     * ID of Appium Session - increments per-session
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long appiumSessionId;

    /**
     * Desired Capabilities object used to configure the
     * AppiumSession
     */
    @Getter @Setter private DesiredCapabilities desiredCapabilities;

    /**
     * Appium-detected device height - used in creating
     * the Swipe Anchor points
     */
    @Getter @Setter private Integer deviceHeight;

    /**
     * Appium-detected device width - used in creating
     * the Swipe Anchor points
     */
    @Getter @Setter private Integer deviceWidth;

    /**
     * Gesture anchor points - used for starting/stopping gestures
     */
    @Getter @Setter private Hashtable<String, Integer> swipeAnchors;

    /**
     * Reference to this Session's driver - NOT persisted to database (indicated by Transient annotation)
     */
    @Transient
    @Getter @Setter private AppiumDriver appiumDriver;

    /**
     * AppiumSessionEntity constructor
     *
     * <p>
     *     This takes a single parameter, DesiredCapabilities, to instantiate
     *     a new AppiumSessionEntity.
     * <p>
     *     IMPORTANT! The AppiumSessionEntity class does not have any validation
     *     code for the Desired Capabilities object - this validation should be
     *     handled in the AppiumSessionService class.
     *
     * @param desiredCapabilities       Validation DesiredCapabilities JSON payload
     */
    public AppiumSessionEntity( DesiredCapabilities desiredCapabilities ) {

        this.desiredCapabilities = desiredCapabilities;
        swipeAnchors = new Hashtable<>();

    }

    /**
     * Utility method to add swipe anchors for this AppiumSessionEntity.
     *
     * <p>
     *     The AppiumSessionService class contains the logic that operates
     *     on the SwipeAnchors for a given AppiumSessionEntity
     * <p>
     *     It is important to note that, when adding coordinates to swipeAnchors,
     *     they are being added ONE COORDINATE AT A TIME - meaning if you wanted
     *     to add the anchor (100, 100) as a swipe point, you would need to make
     *     two calls:
     *
     *          addNewSwipeAnchor( "xValue", 100);
     *          addNewSwipeAnchor( "yValue", 100);
     * <p>
     *     IMPORTANT NOTE ON COORDINATE SYSTEM: Appium's coordinate system
     *     uses "x" and "y" coordinates, but the axes are not as you may expect:
     *     the origin (0,0) for the Appium coordinates is in the TOP-LEFT of
     *     the device display. X axis values increase from left (0) to right (X_MAX)
     *     Y axis values increase from top (0) to bottom (Y_MAX). In other words,
     *     the LOWEST POSSIBLE COORDINATE VALUE is top-left (0,0), and the HIGHEST POSSIBLE
     *     COORDINATE VALUE is bottom-right.
     *
     * @param key               String name for new Anchor to add
     * @param coordinate        Coordinate location of anchor
     */
    public void addNewSwipeAnchor( String key, Integer coordinate ) {

        swipeAnchors.put(key, coordinate);

    }

    /**
     * Convenient method to print out the session properties
     *
     * @return          Formatted string representing session properties
     */
    @Override
    public String toString() {
        return String.format(
                "AppiumSession[" +
                        "appiumSessionId='%s'," +
                        " desiredCapabilities='%s'," +
                        " deviceHeight='%s'," +
                        " deviceWidth='%s'" +
                        " swipeAnchors='%s']",
                appiumSessionId, desiredCapabilities, deviceHeight, deviceWidth, swipeAnchors
        );
    }

}
