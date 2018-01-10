package io.happium.appium_client_service.service;

import io.happium.appium_client_service.persistence.SupportedDesiredCapability;
import io.happium.appium_client_service.persistence.SupportedDesiredCapabilityCrudRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.json.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
 * Service class for Supported Desired Capabilities
 *
 * <p>
 *     This service is used both internally and externally,
 *     meaning that this service is used on application
 *     startup to populated the supported capability data table.
 *     Externally, this service is available to allow enabling
 *     further capabilities not included in Happium.
 */
@Service
public class SupportedDesiredCapabilityService {

    private static String GLOBAL_CAPABILITIES = "global";
    private static String ANDROID_CAPABILITIES = "android";
    private static String IOS_CAPABILITIES = "ios";

    /**
     * Enables interaction with the supported capability data table
     */
    @Getter @Setter private SupportedDesiredCapabilityCrudRepository supportedDesiredCapabilityCrudRepository;

    @Autowired
    public SupportedDesiredCapabilityService( SupportedDesiredCapabilityCrudRepository supportedDesiredCapabilityCrudRepository ) {

        this.supportedDesiredCapabilityCrudRepository = supportedDesiredCapabilityCrudRepository;

    }

    /**
     * Main initializer helper method that drives the instantiation of all capabilities
     * specified in resources/supported_desired_capabilities.json
     *
     * @throws FileNotFoundException            If file cannot be found
     */
    private void _initializeSupportedCapabilityTable() throws FileNotFoundException {

        InputStream fileInput = new FileInputStream( "supported_desired_capabilities.json" );
        JsonReader jsonReader = Json.createReader( fileInput );
        JsonObject supportedCapabilityConfiguration = jsonReader.readObject();  // Returns entire file contents

        // Supported global capabilities configuration
        JsonArray globalCapabilities = supportedCapabilityConfiguration.getJsonArray( GLOBAL_CAPABILITIES );
        _initializeCapabilitySubcategory( globalCapabilities, GLOBAL_CAPABILITIES );

        // Supported Android capabilities configuration
        JsonArray androidCapabilities = supportedCapabilityConfiguration.getJsonArray( ANDROID_CAPABILITIES );
        _initializeCapabilitySubcategory( androidCapabilities, ANDROID_CAPABILITIES );

        // Supported iOS capabilities configuration
        JsonArray iosCapabilities = supportedCapabilityConfiguration.getJsonArray( IOS_CAPABILITIES );
        _initializeCapabilitySubcategory( iosCapabilities, IOS_CAPABILITIES );

    }

    /**
     * Helper method to fully-initialize a Desired Capability
     *
     * @param capabilitySubcategory         e.g. "global", "android" or "ios"
     */
    private void _initializeCapabilitySubcategory( JsonArray capabilitySubcategory, String platform ) {

        for ( JsonValue capability : capabilitySubcategory ) {

            JsonObject capabilityValueAsJsonObject = (JsonObject) capability;

            // Simple Attributes
            String name = capabilityValueAsJsonObject.getString("name");
            boolean isRequired = capabilityValueAsJsonObject.getBoolean("required");

            String description = capabilityValueAsJsonObject.getString("appium_description");

            // Nested JsonObject Attributes
            JsonObject validOptionsObject = capabilityValueAsJsonObject.getJsonObject("valid_options");

            // Nested JsonArray Attributes
            JsonArray alternativeOptionsObject = capabilityValueAsJsonObject.getJsonArray("alternative_options");
            JsonArray alternativeCapabilitiesObject = capabilityValueAsJsonObject.getJsonArray("depends_on");
            JsonArray tips = capabilityValueAsJsonObject.getJsonArray("usage_tips");

            SupportedDesiredCapability newCapability = new SupportedDesiredCapability( name, isRequired, description, platform, validOptionsObject, tips );

            if ( alternativeOptionsObject != null ) {

                newCapability.setAlternativeOptions( alternativeOptionsObject );

            }

            if ( alternativeCapabilitiesObject != null ) {

                newCapability.setAlternativeOptions( alternativeOptionsObject );

            }

            supportedDesiredCapabilityCrudRepository.save( newCapability );

        }

    }



}
