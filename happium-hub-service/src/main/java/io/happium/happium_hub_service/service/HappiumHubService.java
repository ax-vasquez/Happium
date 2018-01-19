package io.happium.happium_hub_service.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.openqa.grid.common.RegistrationRequest;
import org.openqa.grid.common.SeleniumProtocol;
import org.openqa.grid.internal.GridRegistry;
import org.openqa.grid.internal.ProxySet;
import org.openqa.grid.selenium.proxy.DefaultRemoteProxy;
import org.openqa.grid.web.Hub;
import org.openqa.grid.web.servlet.handler.RequestHandler;
import org.openqa.grid.web.servlet.handler.WebDriverRequest;
import org.openqa.grid.web.servlet.handler.WebDriverRequestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;

/**
 * Happium Implementation of Hub class
 *
 * <p>
 *     This class establishes several endpoints for core
 *     interactions with the underlying Selenium Hub.
 *     This class includes logic that enables the use of
 *     Appium sessions for native/hybrid mobile apps.
 */
@Service
@NoArgsConstructor
public class HappiumHubService {

    @Autowired
    @Getter @Setter private Hub hub;

    @Autowired
    @Getter @Setter private WebDriverRequestFactory webDriverRequestFactory;

    /**
     * Starts the hub service (e.g. makes it ready for registering with)
     *
     * @throws Exception
     */
    public void startHubService() throws Exception {

        hub.start();

    }

    /**
     * Stops the internal GridRegistry and then attempts to stop the internal Jetty
     * server - if the Jetty fails to stop, exception is thrown
     *
     * @throws Exception        If Jetty throws exception on shut down
     */
    public void stopHubService() throws Exception {

        hub.stop();

    }

    /**
     * Gets list of known proxies that are each capable of hosting
     * several TestSlots (unless Appium - each proxy should be an
     * individual device. Only one device may host a session at a
     * time).
     */
    public ProxySet getRegisteredProxies() {

        return hub.getRegistry().getAllProxies();

    }

    /**
     * Gets the registry backing up the hub state
     *
     * @return          This Hub's backing registry
     */
    public GridRegistry getBackingRegistry() {

        return hub.getRegistry();

    }

    /**
     * Utility method to obtain the registration endpoint for this
     * HappiumHub instance
     *
     * @return          URL where new nodes register to
     */
    public URL getRegistrationUrl() {

        return hub.getRegistrationURL();

    }

    /**
     * Utility method to obtain the endpoint where a user can request a new
     * WebDriver session on this hub
     *
     * @return          Endpoint where you can request new WebDriver sessions on this Hub
     */
    public URL getWebDriverHubRequestUrl() {

        return hub.getWebDriverHubRequestURL();

    }

    /**
     * Utility method to get the endpoint to view this hub's console output
     *
     * @return          Endpoint for this hub's console output
     */
    public URL getConsoleUrl() {

        return hub.getConsoleURL();

    }

    /**
     * Creates a new Proxy [grid node] registered to this hub using a String
     * representation of a JSON object with all required attributes to configure
     * a new GridNodeConfiguration object
     *
     * @param gridNodeConfigurationJsonString
     */
    public void addNewRemoteProxy( String gridNodeConfigurationJsonString ) {

        DefaultRemoteProxy newRemoteProxy =
                new DefaultRemoteProxy( RegistrationRequest.fromJson( gridNodeConfigurationJsonString ), hub.getRegistry() );
        hub.getRegistry().add( newRemoteProxy );

    }

    /**
     * Utility method to request an additional WebDriverRequest handler be
     * added to this hub's backing GridRegistry
     *
     * @param request               Initial request
     * @param response              Response object to be populated by the server later
     */
    public void requestNewWebDriverHandler ( HttpServletRequest request, HttpServletResponse response ) {

        WebDriverRequest webDriverRequest =
                (WebDriverRequest) webDriverRequestFactory.createFromRequest( request, hub.getRegistry() );

        RequestHandler handler = new RequestHandler( webDriverRequest, response, hub.getRegistry() );

        hub.getRegistry().addNewSessionRequest( handler );

    }

}
