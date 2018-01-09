package io.happium.appium_client_service.service;

import io.happium.appium_client_service.listener.PayloadListener;
import io.happium.appium_client_service.persistence.Payload;
import io.happium.appium_client_service.persistence.PayloadCrudRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.json.JsonObject;

/**
 * Payload Service Class
 *
 * <p>
 *     Drives Payload operations and interacts with the
 *     Payload table via the CRUD repository class.
 */
@Service
public class PayloadService implements PayloadListener {

    /**
     * Enables interacting with the Payload table
     */
    @Getter @Setter private PayloadCrudRepository payloadCrudRepository;

    /**
     * Instantiates Payload service with a CRUD repository class
     *
     * @param payloadCrudRepository         Repository to interface with the Payload table
     */
    @Autowired
    public PayloadService( PayloadCrudRepository payloadCrudRepository ) {

        this.payloadCrudRepository = payloadCrudRepository;

    }

    /**
     * Payload Listener implementation
     *
     * @param payload       New Payload to evaluate
     */
    @Override
    public void onNewPayload( JsonObject payload ) {

        try {

            Payload newPayload = new Payload( payload );
            payloadCrudRepository.save( newPayload );

        } catch ( NullPointerException e ) {

            // One or both required attributes missing

        }

    }
}
