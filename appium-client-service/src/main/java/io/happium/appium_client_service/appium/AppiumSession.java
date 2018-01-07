package io.happium.appium_client_service.appium;

import com.google.gson.JsonObject;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

/** Represents an Appium Session
 * Only one session can run on a server at a time - If multiple sessions are needed, then you must create as many
 * Appium servers as you want concurrent Appium sessions. E.g. If you want to run 3 concurrent Appium sessions, first
 * startup 3 Appium servers, then connect the sessions to those servers.
 *
 * @author Armando Vasquez
 * @author https://www.linkedin.com/in/axvasquez/
 * @author ar.xa.vasquez@gmail.com
 * @version 1.0
 * @since 1.0
 */
@Component
public class AppiumSession {

    static Logger logger = LogManager.getRootLogger();

    private DesiredCapabilities capabilities;
    private String platform; // Currently not in use, but is necessary for adding iOS support later
    private AppiumDriver driver;

    // Device Details
    private Integer deviceWidth;
    private Integer deviceHeight;
    private Hashtable<String, Integer> swipeAnchors;

    /**
     * Basic Constructor - creates empty capabilities object
     */
    public AppiumSession () {
        capabilities = new DesiredCapabilities();
    }

    public void initializeCapabilities( JsonObject capsObj ) {

        Set<String> keys = capsObj.keySet();

        for ( String key : keys ) {

            String valString = capsObj.get( key ).toString();
            capabilities.setCapability( key, valString );

            if ( key.equals( "platformName" ) ) {

                logger.debug("Located platform name: " + valString );

                platform = valString;
            }

        }

    }

    public AppiumDriver getDriver() {
        return driver;
    }

    /**
     * Starts appium session based on the session's platform name (iOS or Android)
     *
     * @param serverUrl                 Address of appium server
     */
    public void startBaseAppiumSession( String serverUrl ) {

        try {

            logger.info("Starting android driver at URL: " + serverUrl );

            driver = new AndroidDriver<MobileElement>( new URL( serverUrl ), capabilities);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Tells the server to wait for the existence of a given element. If timeout is met before element is found, error
     * is generated. If waiting for Xpath, provide the complete relative path as the identifier
     * (e.g. "//android.widget.TextView[@text='someLabelValue']" as the identifier)
     *
     *
     * @param findBy                    String for the identifier type; "id", "name", and "class_name" are supported
     * @param identifier                String of the element's identifier
     * @param timeoutInSeconds          Amount of time to wait for the element's existence
     */
    public void waitForElementExistence (String findBy, String identifier, Integer timeoutInSeconds ) {

        logger.info("Waiting for element existence - Element Identifier: " +  identifier );

        WebDriverWait wait = new WebDriverWait( driver, timeoutInSeconds );

        switch ( findBy ) {
            case "id":
                wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.id( identifier )));
                break;
            case "name":
                wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.name( identifier )));
                break;
            case "class name":
                wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.className( identifier )));
                break;
            case "xpath":
                wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath( identifier )));
                break;
        }
    }

    /**
     * Explicit wait - useful for loading time tolerance testing
     *
     * @param milliseconds              Amount of time to wait for
     */
    public void waitForDuration (Integer milliseconds) {

        logger.info("Explicit Wait: " +  milliseconds + " milliseconds" );

        try {
            Thread.sleep( milliseconds );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Finds an element by a given (supported) locator type and value
     *
     * @param locatorType               Type of locator (e.g. id, className, etc...)
     * @param locator                   Value of identifier
     * @return                          Returns the found element
     */
    public MobileElement locateElement ( String locatorType, String locator) {

        logger.info("Locate and Return Element by " + locatorType + ": " + locator );

        MobileElement element = null;

        try {
            element = (MobileElement) driver.findElement( locatorType, locator );
        } catch ( org.openqa.selenium.NoSuchElementException e ) {
            e.printStackTrace();
        }

        return element;
    }

    /**
     * Finds all elements by a given (supported) locator type and value
     *
     * @param locatorType               Type of locator (e.g. id, className, etc...)
     * @param locator                   Value of identifier
     * @return                          Returns a list of the found elements
     */
    public List locateElements (String locatorType, String locator ) {

        logger.info("Locate and Return List of Elements by " + locatorType + ": " + locator );

        List elements = null;

        try {
            elements = driver.findElements( locatorType, locator );
        } catch ( org.openqa.selenium.NoSuchElementException e ) {
            logger.info("No matching elements found! ");
            e.printStackTrace();
        }

        return elements;
    }

    /**
     * Generates relative Xpath locator string (this is the only "safe" way to use Xpath)
     *
     * NOTE!!! This method must be expanded with each newly-supported element type; this may need to change in the future
     *
     * @param prefixType                String value; use SessionConfig.XpathPrefixTypes to ensure supported values are used
     * @param value                     Explicit identifier for the given element
     * @return                          Fully qualified Xpath locator
     */
    public String generateRelativeXpath ( String prefixType, String value ) {

        String completeRelativeXpath = "";

        switch ( prefixType ) {

            case "//android.widget.Button":
                completeRelativeXpath = "//android.widget.Button"  + value ;
                break;
            case "//android.widget.TextView":
                completeRelativeXpath = "//android.widget.TextView" + value;
        }

        return completeRelativeXpath;

    }

    /**
     * Generates full ID locator for an element
     *
     * @param prefix                    String value; use SessionConfig.IDPrefixTypes to ensure supported values are used
     * @param value                     Most-basic ID for the given element
     * @return                          Fully qualified ID locator
     */
    public String generateID ( String prefix, String value ) {

        return prefix + value;

    }

    /**
     * Dismiss keyboard
     */
    public void dismissKeyboard() {
        driver.hideKeyboard();
    }

    public void initializeGestureEngine( ) {

        logger.debug("Getting Device Height");
        deviceHeight = driver.manage().window().getSize().getHeight();
        logger.debug("Device Height: " + deviceHeight );
        logger.debug("Getting Device Width");
        deviceWidth = driver.manage().window().getSize().getWidth();
        logger.debug("Device Width: " + deviceWidth );

        _initializeSwipeAnchors();

    }

    /**
     * Performs a press action in the center of the given element
     *
     * @param el                    MobileElement to press
     */
    public void pressElement( MobileElement el ) {

        logger.debug("Press Element: " + el.getId() );

        TouchAction action = new TouchAction( driver );
        action.press( ElementOption.element( el )).waitAction().release().perform();
    }

    /**
     * Performs swipe left or right (NOTE: Swiping in a direction moves the view in the opposite direction; e.g.
     * swipe-left scrolls the view right and vice versa)
     *
     * @param direction             Accepted values are "left", and "right"
     */
    public void horizontalSwipe( String direction ) {

        logger.debug("Horizontal Swipe: " + direction );

        String xStartKey = "";
        String yStartKey = "";
        String shiftKey = "";

        switch (direction) {
            case "left":
                xStartKey = "xRight";
                yStartKey = "yMid";
                shiftKey = "xLeft";
                break;
            case "right":
                xStartKey = "xLeft";
                yStartKey = "yMid";
                shiftKey = "xRight";
                break;
            default:
                break;
        }

        Integer xStart = _selectCoordinate( xStartKey );
        Integer yStart = _selectCoordinate( yStartKey );
        Integer shift = _selectCoordinate( shiftKey );

        TouchAction action = new TouchAction( driver );

        action.press( PointOption.point( xStart, yStart ) )
                .waitAction( WaitOptions.waitOptions( Duration.ofMillis(250) ) )
                .moveTo( PointOption.point(shift, yStart))
                .release()
                .perform();

    }

    /**
     * Performs up or down swipe (NOTE: Swiping in a direction moves the view in the opposite direction; e.g.
     * swipe-up scrolls the view down and vice versa)
     *
     * @param direction                 Accepted values are "up", and "down"
     */
    public void verticalSwipe( String direction ) {

        logger.debug("Vertical Swipe: " + direction );

        String xStartKey = "";
        String yStartKey = "";
        String shiftKey = "";

        switch (direction) {
            case "up":
                xStartKey = "xMid";
                yStartKey = "yBot";
                shiftKey = "yTop";
                break;
            case "down":
                xStartKey = "xMid";
                yStartKey = "yTop";
                shiftKey = "yBot";
                break;
        }

        Integer xStart = _selectCoordinate( xStartKey );
        Integer yStart = _selectCoordinate( yStartKey );
        Integer shift = _selectCoordinate( shiftKey );

        TouchAction action = new TouchAction( driver );

        action.press( PointOption.point( xStart, yStart ) )
                .waitAction( WaitOptions.waitOptions( Duration.ofMillis( 250 ) ) )
                .moveTo( PointOption.point( xStart, shift ) )
                .release()
                .perform();
    }

    private void _initializeSwipeAnchors() {

        _initAnchors( deviceHeight,"yTop", "yMid", "yBot", 7 );
        _initAnchors( deviceWidth,"xLeft", "xMid", "xRight", 5 );

    }

    /**
     * Initializes Axis coordinate anchors - used with X-Anchors to create gesture start and stop points.
     */
    private void _initAnchors(Integer metric, String minString, String midString, String maxString, Integer divisor) {
        Integer min = _calculateMinAnchor( metric, divisor );
        Integer mid = _calculateMidpoint( metric / 2 );
        Integer max = _calculateMaxAnchor( metric, min );

        _setAnchor( minString, min );
        _setAnchor( midString, mid );
        _setAnchor( maxString, max );
    }

    /**
     * Calculates the midpoint of an anchor axis
     *
     * @param baseValue             Either the device width or height
     * @return                      Returns midpoint value
     */
    private Integer _calculateMidpoint( Integer baseValue ) {
        return (baseValue / 2);
    }

    /**
     * Calculates the minimum coordinate value of an anchor axis
     *
     * @param baseValue             Either the device width or height
     * @param divisor               Arbitrary value; controls how far in from device edge a gesture will start/stop
     * @return                      Returns minimum coordinate value
     */
    private Integer _calculateMinAnchor( Integer baseValue, Integer divisor ) {
        return (baseValue / divisor);
    }

    /**
     * Calculates the maximum coordinate value of an anchor axis by subtracting the minimum anchor value from total
     *
     * @param baseValue             Either the device width or height
     * @param minAnchor             Value of minimum anchor coordinate for given axis
     * @return                      Returns maximum coordinate value
     */
    private Integer _calculateMaxAnchor( Integer baseValue, Integer minAnchor ) {
        return (baseValue - minAnchor);
    }

    /**
     * Utility method for setting anchor points; keeps code cleaner
     *
     * @param anchorKey             String of the key being stored
     * @param anchorValue           Associated Integer value being stored
     */
    private void _setAnchor( String anchorKey, Integer anchorValue ) {
        swipeAnchors.put(anchorKey, anchorValue);
    }

    /**
     * Returns the associated value for the given key
     *
     * @param key                   Key of the the value to select
     * @return                      associated value
     */
    private Integer _selectCoordinate ( String key ) {
        return swipeAnchors.get(key);
    }

}
