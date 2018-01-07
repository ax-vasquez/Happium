package io.happium.appium_client_service.service;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.happium.appium_client_service.appium.AppiumServerFactory;
import io.happium.appium_client_service.persistence.AppiumServerCrudRepository;
import io.happium.appium_client_service.persistence.AppiumServerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppiumServerService {

    private AppiumServerCrudRepository appiumServerCrudRepository;
    private AppiumServerFactory appiumServerFactory;

    @Autowired
    public AppiumServerService
            ( AppiumServerCrudRepository appiumServerCrudRepository,
              AppiumServiceBuilder appiumServiceBuilder,
              AppiumServerFactory appiumServerFactory) {

        this.appiumServerCrudRepository = appiumServerCrudRepository;
        this.appiumServerFactory = new AppiumServerFactory();

    }

    /**
     * Constructs (but does not start) a new server instance at the target
     * IP.
     *
     * <p>
     *     This method enables the use of multiple different IP addresses,
     *     which is a must in a distributed test environment. Additionally,
     *     through the clever use of naming-conventions, you can implement
     *     your own organization strategy for the known servers.
     *
     * @param targetIpAddress       IP of host to create server on
     * @param serverNickname        Arbitrarily-assigned nickname to provide easy reference to server instance
     */
    public void addNewAppiumServer( String targetIpAddress, String serverNickname ) {

        AppiumDriverLocalService service = appiumServerFactory.buildNewServer( targetIpAddress );
        AppiumServerEntity newServer = new AppiumServerEntity( service , serverNickname );
        appiumServerCrudRepository.save( newServer );

    }

    /**
     * Starts the server specified by the nickname
     *
     * <p>
     *     If the specified server is not found, or if
     *     the server is already running, then nothing
     *     happens (idempotent).
     *
     * @param targetServerNickname      Nickname of server to start
     */
    public void startServerByNickname( String targetServerNickname ) {

        for ( AppiumServerEntity server : appiumServerCrudRepository.findAll() ) {

            if ( server.getNickname().equals( targetServerNickname ) ) {

                if ( !server.serverIsRunning() ) {
                    server.startServer();
                }

            }

        }

    }

    /**
     * Stops the server specified by the nickname
     *
     * <p>
     *     If the specified server is not found, or if
     *     the server has already been stopped, then nothing
     *     happens (idempotent).
     *
     * @param targetServerNickname      Nickname of server to start
     */
    public void stopServerByNickname( String targetServerNickname ) {

        for ( AppiumServerEntity server : appiumServerCrudRepository.findAll() ) {

            if ( server.getNickname().equals( targetServerNickname ) ) {

                if ( server.serverIsRunning() ) {
                    server.stopServer();
                }

            }

        }

    }

    /**
     * Retrieves a list of all known AppiumServerEntities
     *
     * @return          List of known Appium servers
     */
    public List<AppiumServerEntity> getAllKnownAppiumServers() {

        return appiumServerCrudRepository.findAll();

    }

}
