package io.happium.junit_client_service.appium_client_service.appium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Hashtable;
import java.util.List;

/**
 * Drives the simulated user interactions with a physical
 * device in an Appium session.
 */
public class GestureDriver {

    /**
     * X Axis swipe anchor labels
     */
    private final String X_ANCHOR_LEFT = "xLeft";
    private final String X_ANCHOR_MID = "xMiddle";
    private final String X_ANCHOR_RIGHT = "xRight";

    /**
     * Y Axis swipe anchor labels
     */
    private final String Y_ANCHOR_TOP = "yTop";
    private final String Y_ANCHOR_MID = "yMiddle";
    private final String Y_ANCHOR_BOTTOM = "yBottom";

    /**
     * Supported swipe directions
     */
    public enum SWIPE_DIRECTION {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    /**
     * Local copy of AppiumDriver - enables device interaction
     */
    private AppiumDriver driver;

    /**
     * Stores swipe anchor gesture points - used in composing gestures
     */
    private Hashtable<String, Integer> swipeAnchors;

    /**
     * Constructor
     *
     * <p>
     *     This class controls the interactions with a physical device used
     *     in an Appium Session. This class only retains a local copy of the
     *     AppiumDriver so that it may interact with the device in question.
     *     The device height and width are used in calculating the swipe anchor
     *     points.
     * <p>
     *     You can fine-tune the positioning of the swipe anchors by adjusting
     *     the values of the divisors - these values "divide" the screen dimension
     *     in question (height or width) by the provided divisor value. This is done
     *     to ensure the swipe gestures are large enough to register on the device
     *     properly.
     * <p>
     *     A good indication that these divisor values need to be changed is if
     *     your swipe gestures are not able to trigger the desired intent - for
     *     example, a common symptom of too-small divisors is when scrolling
     *     horizontally in a paginated view - if too small, you will see the gesture
     *     occur, but it won't swipe far enough and the current view will simply
     *     shift back to the center without scrolling to the next view.
     *
     * @param driver
     * @param deviceHeight
     * @param deviceWidth
     */
    public GestureDriver( AppiumDriver driver, Integer deviceHeight, Integer deviceWidth ) {

        this.driver = driver;
        _initAnchors( deviceHeight, X_ANCHOR_LEFT, X_ANCHOR_MID, X_ANCHOR_RIGHT,  7 );
        _initAnchors( deviceWidth, Y_ANCHOR_TOP, Y_ANCHOR_MID, Y_ANCHOR_BOTTOM, 5 );

    }

    /**
     * Tells the server to wait for the existence of a given element.
     * If timeout is met before element is found, error is generated.
     *
     *
     * @param findBy                    String for the identifier type
     * @param identifier                String of the element's identifier
     * @param timeoutInSeconds          Amount of time to wait for the element's existence
     */
    public void waitForElementExistence (String findBy, String identifier, Integer timeoutInSeconds ) {

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
     * Finds an element by a given (supported) locator type and value
     *
     * @param locatorType               Type of locator (e.g. id, className, etc...)
     * @param locator                   Value of identifier
     * @return                          Returns the found element
     */
    public MobileElement locateElement (String locatorType, String locator) {

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

        List elements = null;

        try {
            elements = driver.findElements( locatorType, locator );
        } catch ( org.openqa.selenium.NoSuchElementException e ) {
            e.printStackTrace();
        }

        return elements;
    }

    /**
     * Performs a press action in the center of the given element
     *
     * @param el                    MobileElement to press
     */
    public void pressElement( MobileElement el ) {

        TouchAction action = new TouchAction( driver );
        action.press( ElementOption.element( el )).waitAction().release().perform();
    }

    /**
     * Dismiss keyboard
     */
    public void dismissKeyboard() {

        driver.hideKeyboard();

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

    /**
     * Performs swipe left or right (NOTE: Swiping in a direction moves the view in the opposite direction; e.g.
     * swipe-left scrolls the view right and vice versa)
     *
     * @param direction             Accepted values are "left", and "right"
     */
    public void horizontalSwipe( SWIPE_DIRECTION direction ) {

        String xStartKey = "";
        String yStartKey = "";
        String shiftKey = "";

        switch (direction) {
            case LEFT:
                xStartKey = X_ANCHOR_RIGHT;
                yStartKey = Y_ANCHOR_MID;
                shiftKey = X_ANCHOR_LEFT;
                break;
            case RIGHT:
                xStartKey = X_ANCHOR_LEFT;
                yStartKey = Y_ANCHOR_MID;
                shiftKey = X_ANCHOR_RIGHT;
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
    public void verticalSwipe( SWIPE_DIRECTION direction ) {

        String xStartKey = "";
        String yStartKey = "";
        String shiftKey = "";

        switch (direction) {
            case UP:
                xStartKey = X_ANCHOR_MID;
                yStartKey = Y_ANCHOR_BOTTOM;
                shiftKey = Y_ANCHOR_TOP;
                break;
            case DOWN:
                xStartKey = X_ANCHOR_MID;
                yStartKey = Y_ANCHOR_TOP;
                shiftKey = Y_ANCHOR_BOTTOM;
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

}
