package io.happium.appium_client_service.listener;


import javax.json.JsonObject;

/**
 * Listener interface to detect new payloads
 */
public interface PayloadListener {

    void onNewPayload( JsonObject payload );

}
