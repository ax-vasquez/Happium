package io.happium.appium_client_service.persistence;

import lombok.Getter;
import lombok.Setter;

import javax.json.JsonObject;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Represents any given payload sent to start a new HappiumSession
 *
 * <p>
 *     This model is used in the first layer of validation - the only
 *     requirements are that there is 1). a "desiredCapabilities" attribute
 *     within the payload (should be a nested javax.json.JsonObject) and
 *     2). "instructions" attribute, which should also be a nested JsonObject.
 *
 * <p>
 *     Beyond those two attributes, no further validation is done by the
 *     Payload service. It is up to the HappiumService to validate that
 *     the "desiredCapabilities" and "instructions" attributes are valid.
 */
@Entity
public class Payload implements Serializable {

    /**
     * Auto-incremented, serialized ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id;

    /**
     * JsonObject to configure an AppiumSession
     */
    @Getter @Setter private JsonObject desiredCapabilities;

    /**
     * Use this attribute to configure your custom test setup needs
     */
    @Getter @Setter private JsonObject instructions;

    public Payload( JsonObject rawPayload ) {

        this.desiredCapabilities = rawPayload.getJsonObject("desiredCapabilities");
        this.instructions = rawPayload.getJsonObject("instructions");

    }


}
